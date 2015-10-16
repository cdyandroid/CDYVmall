package parfois.com.cdyvmall.adapter.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import parfois.com.cdyvmall.adapter.bean.ActivityPrds;
import parfois.com.cdyvmall.adapter.bean.Ads;
import parfois.com.cdyvmall.adapter.bean.Category;
import parfois.com.cdyvmall.adapter.bean.Goods;
import parfois.com.cdyvmall.adapter.bean.Product;
import parfois.com.cdyvmall.adapter.bean.SGoods;


public class PaserUtils {
	
	public static Map<String,Object> parserHomeAdsJson(byte[] buff) {
		Map<String,Object> map=new HashMap<String, Object>();
		List<Ads> list0=null;
		List<Ads> list1=null;
		Ads ad=null;
		try {
			JSONObject jsonObject=new JSONObject(new String(buff));
			list0=new ArrayList<Ads>();
			JSONArray ja1=jsonObject.getJSONArray("locationAds");
			for (int i = 0; i < ja1.length(); i++) {
				JSONObject jo=ja1.getJSONObject(i);
				ad=new Ads();
				ad.setAdPicUrl(jo.getString("adPicUrl"));
				ad.setAdPrdUrl(jo.getString("adPrdUrl"));
				list0.add(ad);
			}
			map.put("locationAds", list0);
			
			list1=new ArrayList<Ads>();
			JSONArray ja2=jsonObject.getJSONArray("scrollAds");
			for (int i = 0; i < ja2.length(); i++) {
				JSONObject jo=ja2.getJSONObject(i);
				ad=new Ads();
				ad.setAdPicUrl(jo.getString("adPicUrl"));
				ad.setAdPrdUrl(jo.getString("adPrdUrl"));
				list1.add(ad);
			}
			map.put("scrollAds", list1);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String,Object> parserHomeListJson(byte[] buff) {
		Map<String,Object> map=new HashMap<String, Object>();
		List<Goods> list0=null;
		List<Goods> list1=null;
		Goods goods=null;
		try {
			JSONObject jsonObject=new JSONObject(new String(buff));
			JSONArray jsonArray=jsonObject.getJSONArray("regionList");
			list0=new ArrayList<Goods>();
			JSONObject jObject0=jsonArray.getJSONObject(0);
			JSONArray jArray0=jObject0.getJSONArray("productList");
			for (int i = 0; i < jArray0.length(); i++) {
				JSONObject jo=jArray0.getJSONObject(i);
				goods=new Goods();
				goods.setPrdDescription(jo.getString("prdDescription"));
				goods.setPrdName(jo.getString("prdName"));
				goods.setPrdPicUrl(jo.getString("prdPicUrl"));
				goods.setPrdStatus(jo.getInt("prdStatus"));
				goods.setPrdUnitPrice(jo.getString("prdUnitPrice"));
				goods.setIsDisplayPrice(jo.getInt("isDisplayPrice"));
				goods.setPrdId(jo.getString("prdId"));
				list0.add(goods);
			}
			map.put(jObject0.getString("name"), list0);
			list1=new ArrayList<Goods>();
			JSONObject jObject1=jsonArray.getJSONObject(1);
			JSONArray jArray1=jObject1.getJSONArray("productList");
			for (int i = 0; i < jArray1.length(); i++) {
				JSONObject jo=jArray1.getJSONObject(i);
				goods=new Goods();
				goods.setPrdDescription(jo.getString("prdDescription"));
				goods.setPrdName(jo.getString("prdName"));
				goods.setPrdPicUrl(jo.getString("prdPicUrl"));
				goods.setPrdStatus(jo.getInt("prdStatus"));
				goods.setPrdUnitPrice(jo.getString("prdUnitPrice"));
				goods.setIsDisplayPrice(jo.getInt("isDisplayPrice"));
				goods.setPrdId(jo.getString("prdId"));
				list1.add(goods);
			}
			map.put(jObject1.getString("name"), list1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static List<String> parserHomeHotJson(byte[] buff){
		List<String> list=null;
		try {
			JSONObject jsonObject=new JSONObject(new String(buff));
			list=new ArrayList<String>();
			list.add(jsonObject.getString("defalutSearchWord"));
			JSONArray jsonArray=jsonObject.getJSONArray("hotWordList");
			for (int i = 0; i < jsonArray.length(); i++) {
				list.add(jsonArray.getString(i));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public static Map<String,Object> parserHonorListJson(byte[] buff){
		Map<String,Object> map=new HashMap<String, Object>();
		List<ActivityPrds> listap=null;
		List<Goods> listoh=null;
		List<Goods> listcf=null;
		ActivityPrds ap=null;
		Goods g=null;
		try {
			JSONObject jsonObject=new JSONObject(new String(buff));
			JSONObject jObject=jsonObject.getJSONObject("result");
			listap=new ArrayList<ActivityPrds>();
			JSONArray jsonArray=jObject.getJSONArray("activityPrds");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo=jsonArray.getJSONObject(i);
				ap=new ActivityPrds();
				ap.setActivityDescription(jo.getString("activityDescription"));
				ap.setActivityPicUrl(jo.getString("activityPicUrl"));
				ap.setActivityStatus(jo.getInt("activityStatus"));
				ap.setActivityTime(jo.getString("activityTime"));
				ap.setFittingDescription(jo.getString("fittingDescription"));
				ap.setFittingPicUrl(jo.getString("fittingPicUrl"));
				ap.setFittingTitle(jo.getString("fittingTitle"));
				ap.setPrdUrl(jo.getString("prdUrl"));
				listap.add(ap);
			}
			map.put("activityPrds", listap);
			listoh=new ArrayList<Goods>();
			JSONObject jsonObj=jObject.getJSONObject("otherHoners");
			JSONArray jArray=jsonObj.getJSONArray("productInfoList");
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jo=jArray.getJSONObject(i);
				g=new Goods();
				g.setPrdStatus(jo.getInt("prdStatus"));
				g.setPrdName(jo.getString("prdName"));
				g.setPrdDescription(jo.getString("prdDescription"));
				g.setPrdPicUrl(jo.getString("prdPicUrl"));
				g.setPrdId(jo.getString("prdId"));
				listoh.add(g);
			}
			map.put("otherHoners", listoh);
			listcf=new ArrayList<Goods>();
			JSONObject jObj=jObject.getJSONObject("commonFitting");
			JSONArray jArr=jObj.getJSONArray("productInfoList");
			for (int i = 0; i < jArr.length(); i++) {
				JSONArray ja=jArr.getJSONArray(i);
				for (int j = 0; j < ja.length(); j++) {
					JSONObject jo=ja.getJSONObject(j);
					g=new Goods();
					g.setPrdName(jo.getString("prdName"));
					g.setPrdPicUrl(jo.getString("prdPicUrl"));
					g.setPrdId(jo.getString("prdId"));
					listcf.add(g);
				}
			}
			map.put("commonFitting", listcf);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return map;
	}

	public static List<Category> parserCategoryJson(byte[] buff){
		List<Category> list=null;
		List<Product> plist=null;
		Category cg=null;
		Product pd=null;
		try {
			JSONObject jsonObject=new JSONObject(new String(buff));
			list=new ArrayList<Category>();
			JSONArray jsonArray=jsonObject.getJSONArray("appCategoryList");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo=jsonArray.getJSONObject(i);
				cg=new Category();
				cg.setName(jo.getString("name"));
				plist=new ArrayList<Product>();
				JSONArray ja=jo.getJSONArray("secondCategorys");
				for (int j = 0; j < ja.length(); j++) {
					JSONObject obj=ja.getJSONObject(j);
					pd=new Product();
					pd.setName(obj.getString("name"));
					pd.setPicUrl(obj.getString("picUrl"));
					pd.setCategoryUrl(obj.getString("categoryUrl"));
					plist.add(pd);
				}
				cg.setList(plist);
				list.add(cg);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public static List<SGoods> parserSearchListJson(byte[] buff) {
		List<SGoods> list=new ArrayList<SGoods>();
		SGoods sg=null;
		try {
			JSONObject jsonObject=new JSONObject(new String(buff));
			JSONArray jsonArray=jsonObject.getJSONArray("prdList");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo=jsonArray.getJSONObject(i);
				sg=new SGoods();
				sg.setName(jo.getString("name"));
				sg.setPicUrl(jo.getString("picUrl"));
				sg.setPrdId(jo.getString("prdId"));
				sg.setPrdStatus(jo.getInt("prdStatus"));
				sg.setPrice(jo.getString("price"));
				sg.setPromotionWord(jo.getString("promotionWord"));
				list.add(sg);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
}







