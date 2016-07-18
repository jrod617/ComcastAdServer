package com.crunchify.restjersey;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "campaign")
public class AdCampaign implements Serializable {

   private static final long serialVersionUID = 1L;
   
   // Unique String representing the Partner Id
   private String partnerId;
   // How long an ad campaign should run
	private double duration;
	// Content of the ad campaign
	private String adContent;
	// Allows for the partner to identify different ad campaigns
	private int adId;
	// Date when this campaign was created
	private Date creationDate;

	public AdCampaign() {
		this.partnerId = "MUTINY";
		this.duration = 1986.0;
		this.adContent = "<INIT>";
		this.adId = 1;
		this.creationDate = new Date();
	}

   public AdCampaign(String partnerId, double duration, String adContent){
      this.partnerId = partnerId;
      this.duration = duration;
      this.adContent = adContent;
      this.adId = 1;
      this.creationDate = new Date();
   }
   
   public AdCampaign(String partnerId, double duration, String adContent, int adId){
	  this.partnerId = partnerId;
	  this.duration = duration;
	  this.adContent = adContent;
	  this.adId = adId;
	  this.creationDate = new Date();
   }

   @XmlElement
   public void setId(String partnerId) {
      this.partnerId = partnerId;
   }
   public String getId() {
      return partnerId;
   }
   
   @XmlElement
   public void setDuration(double duration) {
      this.duration = duration;
   }
   public double getDuration() {
      return duration;
   }
   
   @XmlElement
   public void setAdContent(String adContent) {
	   this.adContent = adContent;
   }
   public String getAdContent() {
      return adContent;
   }
   
   @XmlElement
   public void setAdId(int adId) {
      this.adId = adId;
   }	
   public int getAdId() {
      return adId;
   }
   
   public Date getCreationDate() {
	   return creationDate;
   }

   @Override
   public boolean equals(Object object){
      if(object == null){
         return false;
      }else if(!(object instanceof AdCampaign)){
         return false;
      }else {
    	  AdCampaign campaign = (AdCampaign)object;
         if(partnerId.equals(campaign.getId())
            && duration == campaign.getDuration()
            && adContent.equals(campaign.getAdContent())
            && adId == campaign.getAdId()
            && creationDate.equals(campaign.getCreationDate()))
         {
            return true;
         }			
      }
      return false;
   }
   
   public String toString() {
	   return "{ \"partner_id\": \"" + partnerId + "\","
			   + "\"duration\": \"" + duration + "\","
			   + "\"ad_content\": \"" + adContent + "\","
			   + "\"ad_id\": \"" + adId + "\","
			   + "\"creation_date\": \"" + creationDate.toString()
			   +"\"}";
   }
}
