package com.example.demo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {

	public void connet(int port ,String host) throws InterruptedException{
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			Bootstrap bs = new Bootstrap();
			bs.group(group)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
	
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new TimeClientHandler());
					}
					
				});
			ChannelFuture f = bs.connect(host,port).sync();
			f.channel().closeFuture().sync();
		}
		finally {
			group.shutdownGracefully();
		}
	}
	
	private class TimeClientHandler extends ChannelHandlerAdapter{
		
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			byte[] bs = "QUERY TIME".getBytes();
			ByteBuf buf = Unpooled.buffer(bs.length);
			buf.writeBytes(bs);
			ctx.writeAndFlush(buf);
		}
		
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			ByteBuf bf = (ByteBuf)msg;
			byte[] bts = new byte[bf.readableBytes()];
			bf.readBytes(bts);
			String body = new String(bts,"UTF-8");
			System.out.println("客户端收到的消息："+body);
		}
		
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			ctx.close();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new TimeClient().connet(8080, "127.0.0.1");
	}
}
