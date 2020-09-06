package com.magicguang.netty.helloword.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public class HelloWorldClient {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new HelloWorldClientInit());
        try {
            Channel channel = bootstrap.connect("127.0.0.1", 8090).sync().channel();
            ChannelFuture channelFuture = null;
            Scanner scanner = new Scanner(System.in);
            for (;;){
                String nextLineStr = scanner.nextLine();
                if (nextLineStr == null){
                    break;
                }
                channelFuture = channel.writeAndFlush(nextLineStr + "\r\n");

                if ("exit".equals(nextLineStr)){
                    channel.closeFuture().sync();
                    break;
                }
                if (channelFuture != null){
                    channelFuture.sync();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}
