package com.crunchify.restjersey;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AdCampaignManager {

	public List<AdCampaign> getAllCampaigns() {
		List<AdCampaign> campaignList = null;
		try {
			File file = new File("AdCampaigns.dat");
			if (!file.exists()) {
				AdCampaign campaign = new AdCampaign("MUTINY", 1986.0, "INIT");
				campaignList = new ArrayList<AdCampaign>();
				campaignList.add(campaign);
				saveCampaignList(campaignList);
			} else {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				campaignList = (List<AdCampaign>) ois.readObject();
				ois.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return campaignList;
	}

	public AdCampaign getCampaign(String id) {
		List<AdCampaign> campaigns = getAllCampaigns();

		for (AdCampaign campaign : campaigns) {
			if (campaign.getId() == id) {
				return campaign;
			}
		}
		return null;
	}

	public int addCampaign(AdCampaign pCampaign) {
		List<AdCampaign> campaignList = getAllCampaigns();
		List<AdCampaign> foundList = new ArrayList<AdCampaign>();
		boolean campaignExists = false;
		for (AdCampaign campaign : campaignList) {
			if (campaign.getId().equals(pCampaign.getId())) {
				campaignExists = true;
				foundList.add(campaign);
			}
		}
		
		if (campaignExists) {
			int nextValidAdId = 2;
			for (AdCampaign campaign : foundList) {
				
				// Found an Exact Match -> return 0
				if(campaign.equals(pCampaign)) {
					return 0;
				}
				
				if(campaign.getAdId() > nextValidAdId) {
					nextValidAdId = campaign.getAdId() + 1;
				}
			}
			pCampaign.setAdId(nextValidAdId);
		}

		campaignList.add(pCampaign);
		saveCampaignList(campaignList);
		return 1;
	}

	public int updateCampaign(AdCampaign pCampaign) {
		List<AdCampaign> campaignList = getAllCampaigns();

		for (AdCampaign campaign : campaignList) {
			if (campaign.getId() == pCampaign.getId()) {
				int index = campaignList.indexOf(campaign);
				campaignList.set(index, pCampaign);
				saveCampaignList(campaignList);
				return 1;
			}
		}
		return 0;
	}

	public int deleteCampaign(String id) {
		List<AdCampaign> campaignList = getAllCampaigns();

		for (AdCampaign campaign : campaignList) {
			if (campaign.getId() == id) {
				int index = campaignList.indexOf(campaign);
				campaignList.remove(index);
				saveCampaignList(campaignList);
				return 1;
			}
		}
		return 0;
	}

	private void saveCampaignList(List<AdCampaign> campaignList) {
		try {
			File file = new File("AdCampaigns.dat");
			FileOutputStream fos;

			fos = new FileOutputStream(file);

			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(campaignList);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
