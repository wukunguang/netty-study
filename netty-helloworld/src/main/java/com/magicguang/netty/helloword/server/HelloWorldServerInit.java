package com.magicguang.netty.helloword.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class HelloWorldServerInit extends ChannelInitializer<SocketChannel> {
    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();
    private static final HelloWorldServerHandler SERVER_HANDLER = new HelloWorldServerHandler();
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();

        //防止粘包？？
        channelPipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        //编码器解码器
        channelPipeline.addLast(DECODER);
        channelPipeline.addLast(ENCODER);


        //接入业务逻辑实现
        channelPipeline.addLast(SERVER_HANDLER);

    }
}
