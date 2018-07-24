package com.example.demo.netty;

import java.time.LocalDateTime;

import io.netty.bootstrap.ServerBootstrap;
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
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {

	public void bind(int port) throws InterruptedException{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(bossGroup, workGroup)
			    .channel(NioServerSocketChannel.class)
			    .option(ChannelOption.SO_BACKLOG,1024)
			    .childHandler(new ChildChannelHandler());
			ChannelFuture f = sb.bind(port).sync();
			f.channel().closeFuture().sync();
		}
		finally{
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new TimeServerHandler());
		}

		
	}
	
	private class TimeServerHandler extends ChannelHandlerAdapter{
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			ByteBuf buffer = (ByteBuf)msg;
			byte[] req = new byte[buffer.readableBytes()];
			buffer.readBytes(req);
			String boy = new String(req,"UTF-8");
			System.out.println("服务器收到的消息是:"+boy);
			String nowTime = "QUERY TIME".equalsIgnoreCase(boy) ? LocalDateTime.now().toString() : "BAD ORDER";
			ByteBuf resp = Unpooled.copiedBuffer(nowTime.getBytes());
			ctx.write(resp);
		}
		
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
		}
		
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			ctx.close();
		}
	}
	
	public static void main(String[] args) {
		try {
			new TimeServer().bind(8080);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
