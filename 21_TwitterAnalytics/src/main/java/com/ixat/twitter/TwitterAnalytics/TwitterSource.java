package com.ixat.twitter.TwitterAnalytics;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.source.AbstractSource;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterSource extends AbstractSource
implements EventDrivenSource, Configurable  {

	TwitterStream  twitterApi;
	FilterQuery fq ;
	TwitterStatusListener listener;
    
	@Override
	public void configure(Context context) {
		String consumerKey = context.getString("consumerKey");
		String consumerSecret = context.getString("consumerSecret");
		String accessToken = context.getString("accessToken");
		String accessTokenSecret = context.getString("accessTokenSecret");
		 
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(consumerKey);
		cb.setOAuthConsumerSecret(consumerSecret);
		cb.setOAuthAccessToken(accessToken);
		cb.setOAuthAccessTokenSecret(accessTokenSecret);
		cb.setJSONStoreEnabled(true);
		
		String keywordString = context.getString("keywords", "");
		
		fq = new FilterQuery();
		fq.track(keywordString.split(","));
		
		twitterApi =   new TwitterStreamFactory(cb.build()).getInstance();
	}
	
	public void start() {
		
		ChannelProcessor channel = getChannelProcessor();
		listener = new TwitterStatusListener(channel);
		twitterApi.addListener(listener);
		
		twitterApi.filter(fq);
		
		super.start();
	}

	public void stop(){
		twitterApi.shutdown();
		super.stop();
	}
	
	
	class TwitterStatusListener implements StatusListener{
		
		private ChannelProcessor channel;
		
		public TwitterStatusListener(ChannelProcessor channel){
			this.channel = channel;
		}

		@Override
		public void onException(Exception arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDeletionNotice(StatusDeletionNotice arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onScrubGeo(long arg0, long arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStallWarning(StallWarning arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatus(Status arg0) {
			// TODO Auto-generated method stub
			String tweet  = arg0.getText();
			Event event = EventBuilder.withBody( tweet.getBytes());
			
			channel.processEvent(event);
		}

		@Override
		public void onTrackLimitationNotice(int arg0) {
			// TODO Auto-generated method stub
			
		}
		
		
		
	}
}
