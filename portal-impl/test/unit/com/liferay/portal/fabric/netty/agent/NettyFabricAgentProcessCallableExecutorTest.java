/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.fabric.netty.agent;

import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.fabric.netty.NettyTestUtil;
import com.liferay.portal.fabric.netty.rpc.handlers.NettyRPCChannelHandler;
import com.liferay.portal.fabric.status.JMXProxyUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.embedded.EmbeddedChannel;

import java.io.Serializable;

import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricAgentProcessCallableExecutorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testExecute() throws Exception {
		EmbeddedChannel embeddedChannel =
			NettyTestUtil.createEmptyEmbeddedChannel();

		ChannelPipeline channelPipeline = embeddedChannel.pipeline();

		channelPipeline.addFirst(
			NettyRPCChannelHandler.NAME, NettyRPCChannelHandler.INSTANCE);

		JMXProxyUtil.ProcessCallableExecutor processCallableExecutor =
			new NettyFabricAgentProcessCallableExecutor(embeddedChannel);

		NoticeableFuture<Serializable> noticeableFuture =
			processCallableExecutor.execute(
				new ProcessCallable<Serializable>() {

					@Override
					public Serializable call() {
						return StringPool.BLANK;
					}

				});

		embeddedChannel.writeOneInbound(embeddedChannel.readOutbound());
		embeddedChannel.writeOneInbound(embeddedChannel.readOutbound());

		Assert.assertEquals(StringPool.BLANK, noticeableFuture.get());

		final ProcessException processException = new ProcessException("");

		noticeableFuture = processCallableExecutor.execute(
			new ProcessCallable<Serializable>() {

				@Override
				public Serializable call() throws ProcessException {
					throw processException;
				}

			});

		embeddedChannel.writeOneInbound(embeddedChannel.readOutbound());
		embeddedChannel.writeOneInbound(embeddedChannel.readOutbound());

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException executionException) {
			Assert.assertSame(processException, executionException.getCause());
		}
	}

}