package Global.Unity;

import android.util.Log;

import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;

public class BannerListner implements BannerView.IListener {

    String text;

    public BannerListner(String text){
        this.text = text;
    }

    @Override
    public void onBannerLoaded(BannerView bannerView) {
        Log.i("SuppprtTest_globalClass", text +": Banner Loaded id:" + bannerView.getPlacementId());
    }

    @Override
    public void onBannerClick(BannerView bannerView) {
    }

    @Override
    public void onBannerFailedToLoad(BannerView bannerView, BannerErrorInfo bannerErrorInfo) {
        Log.i("SuppprtTest_globalClass", bannerErrorInfo.errorMessage);

    }

    @Override
    public void onBannerLeftApplication(BannerView bannerView) {

    }
}
