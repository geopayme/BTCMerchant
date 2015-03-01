package info.blockchain.merchant;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.net.Uri;
//import android.util.Log;

public class AboutActivity extends Activity	{
	
	private TextView tvAbout = null;
	private Button bRate = null;
	private Button bSupport = null;
	private Button bDownload = null;
	private String strWalletPackage = "piuk.blockchain.android";

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    this.setContentView(R.layout.activity_about);

        tvAbout = (TextView)findViewById(R.id.about);
        tvAbout.setText(getString(R.string.about, "1.2.4", "2014"));

        bRate = (Button)findViewById(R.id.rate_us);
        bRate.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	String appPackageName = getPackageName();
            	Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
            	marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            	startActivity(marketIntent);
            }
        });
    
        bSupport = (Button)findViewById(R.id.support);
        bSupport.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","support@blockchain.zendesk.com", null));
            	emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Blockchain Merchant Tech Support");
            	startActivity(Intent.createChooser(emailIntent, AboutActivity.this.getResources().getText(R.string.email_chooser)));
            }
        });

        bDownload = (Button)findViewById(R.id.free_wallet);
        if(hasWallet())	{
        	bDownload.setVisibility(View.GONE);
        }
        else	{
            bDownload.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                	Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + strWalletPackage));
                	startActivity(marketIntent);
                }
            });
        }

    }

    private boolean hasWallet()	{
    	PackageManager pm = this.getPackageManager();
    	try	{
    		pm.getPackageInfo(strWalletPackage, 0);
    		return true;
    	}
    	catch(NameNotFoundException nnfe)	{
    		return false;
    	}
    }

}
