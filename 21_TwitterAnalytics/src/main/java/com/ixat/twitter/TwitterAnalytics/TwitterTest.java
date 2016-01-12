package com.ixat.twitter.TwitterAnalytics;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterTest {

	public static void main(String[] args) throws InterruptedException {
		String consumerKey = "ESHO3IKmWS7vgvzbZDvN7FVeZ";
    	String consumerSecret = "Rurs34lomZYX9Omx3lADTkOSqx7aDcNEZ2Yv5PnH1c09k8jEF8";
    	String token = "329271601-U0ezMTw2mssG9pARidPf7FoVkT5vy2VOSAJPBeBs";
    	String secret = "5uyU62XSxtBpwO9kXRD5W4NFAtVLztNAuaKMDWL3Hfkdb";
    	
        run(consumerKey,consumerSecret,token,secret);

	}
	 public static void run(String consumerKey, String consumerSecret, String token, String secret) throws InterruptedException {

    	 ConfigurationBuilder cb = new ConfigurationBuilder();
         cb.setDebugEnabled(true);
         cb.setOAuthConsumerKey(consumerKey);
         cb.setOAuthConsumerSecret(consumerSecret);
         cb.setOAuthAccessToken(token);
         cb.setOAuthAccessTokenSecret(secret);

         TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

         StatusListener listener = new StatusListener() {
 
             public void onException(Exception arg0) {
                 // TODO Auto-generated method stub

             }

       
             public void onDeletionNotice(StatusDeletionNotice arg0) {
                 // TODO Auto-generated method stub

             }

      
             public void onScrubGeo(long arg0, long arg1) {
                 // TODO Auto-generated method stub

             }

            
             public void onStatus(Status status) {
                 /*User user = status.getUser();
                 
                 // gets Username
                 String username = status.getUser().getScreenName();
                 System.out.println(username);
                 String profileLocation = user.getLocation();
                 System.out.println(profileLocation);
                 long tweetId = status.getId(); 
                 System.out.println(tweetId);
                 String content = status.getText();
                 System.out.println(content +"\n");

*/
            	 System.out.println(status.getId() + "  --> " + status.getText());
             }

        
             public void onTrackLimitationNotice(int arg0) {
                 // TODO Auto-generated method stub

             }

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}

         };
         FilterQuery fq = new FilterQuery();
     
         String keywords[] = {"obama","trump","gun ban"};

         fq.track(keywords);
         
         twitterStream.addListener(listener);
         twitterStream.filter(fq);  
        
     }
}
