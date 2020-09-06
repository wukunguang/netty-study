package com.magicguang.netty.helloword.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class HelloWorldServer {
    public static void main(String[] args) {
        // 创建主线程组，
        EventLoopGroup serverGroup = new NioEventLoopGroup(1);
        // worker线程组，对socketChannel进行处理？
        EventLoopGroup workGroup = new NioEventLoopGroup();


        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(serverGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.WARN))
                    .childHandler(new HelloWorldServerInit());
            ChannelFuture channelFuture = serverBootstrap.bind(8090);
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            serverGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
