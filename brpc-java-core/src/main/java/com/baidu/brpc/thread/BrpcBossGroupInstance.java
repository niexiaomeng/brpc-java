/*
 * Copyright (c) 2018 Baidu, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.baidu.brpc.thread;

import com.baidu.brpc.utils.CustomThreadFactory;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * client & server io thread pool single instance
 */

public class BrpcBossGroupInstance {

    private static volatile EpollEventLoopGroup epollThreadPool;

    private static volatile NioEventLoopGroup nioThreadPool;

    private BrpcBossGroupInstance() {

    }

    /**
     * threadNum only works when thread pool instance create in the first time
     */
    public static EventLoopGroup getOrCreateEpollInstance(int threadNum) {
        if (epollThreadPool == null) {
            synchronized(BrpcBossGroupInstance.class) {
                if (epollThreadPool == null) {
                    epollThreadPool = new EpollEventLoopGroup(threadNum,
                            new CustomThreadFactory("server-acceptor-thread"));

                }
            }
        }
        return epollThreadPool;
    }

    /**
     * threadNum only works when thread pool instance create in the first time
     */
    public static EventLoopGroup getOrCreateNioInstance(int threadNum) {
        if (nioThreadPool == null) {
            synchronized(BrpcBossGroupInstance.class) {
                if (nioThreadPool == null) {
                    nioThreadPool = new NioEventLoopGroup(threadNum,
                            new CustomThreadFactory("server-acceptor-thread"));

                }
            }
        }
        return nioThreadPool;
    }

    public static NioEventLoopGroup getNioInstance() {
        return nioThreadPool;
    }

    public static EpollEventLoopGroup getEpollInstance() {
        return epollThreadPool;
    }
}
