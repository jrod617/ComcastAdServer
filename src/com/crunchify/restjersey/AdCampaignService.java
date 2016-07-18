package com.crunchify.restjersey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

@Path("/")
public class AdCampaignService {
	
	AdCampaignManager campaignManager = new AdCampaignManager();

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AdCampaign> getCampaigns() {
		return campaignManager.getAllCampaigns();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCampaign(InputStream incomingData) {
		StringBuilder jsonBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				jsonBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
			return Response.status(400).build();
		}
		System.out.println("Data Received: " + jsonBuilder.toString());
 
		JSONObject jCampaign = new JSONObject(jsonBuilder.toString());
		if(jCampaign.length() != 3 
				|| !jCampaign.has("partner_id")
				|| !jCampaign.has("duration")
				|| !jCampaign.has("ad_content")) {
			return Response.status(400).build();
		}
		
		String partnerId = jCampaign.getString("partner_id");
		double duration = jCampaign.getDouble("duration");
		String adContent = jCampaign.getString("ad_content");
		
		AdCampaign pCampaign = new AdCampaign(partnerId, duration, adContent);
		int errorCode = campaignManager.addCampaign(pCampaign);
		// return HTTP response 200 in case of success
		if(errorCode == 1) {
			return Response.status(200).build();
		}
		return Response.status(500).build();
	}
	
	@Path("{c}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCampaign(@PathParam("c") String c) {
		List<AdCampaign> allCampaigns = campaignManager.getAllCampaigns();
		List<AdCampaign> validCampaigns = new ArrayList<AdCampaign>();
		
		for (AdCampaign campaign : allCampaigns) {
			if(campaign.getId().equals(c)) {
				System.out.println("Found a campaign with a matching id: " + campaign.getId());
				Date validDuration = new Date((long) (campaign.getCreationDate().getTime() + (campaign.getDuration()*1000)));
				if( validDuration.after(new Date() )) {
					validCampaigns.add(campaign);
					System.out.println("Found a valid duration");
				}
			}
		}
		
		if(validCampaigns.size() > 0) {
			StringBuilder jsonBuilder = new StringBuilder();
			System.out.println("Number of Valid campaigns: " + validCampaigns.size());
			jsonBuilder.append("[");
			for (AdCampaign campaign : validCampaigns) {
				jsonBuilder.append(campaign.toString() + ",");
			}
			jsonBuilder.append("]");
			System.out.println(jsonBuilder.toString());
			JSONArray jsonOut = new JSONArray(jsonBuilder.toString());
			return Response.status(200).entity(jsonOut.toString()).build();
		}
		
		return Response.status(500).build();
	}

}
