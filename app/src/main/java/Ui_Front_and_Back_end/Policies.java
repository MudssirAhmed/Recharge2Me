package Ui_Front_and_Back_end;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.recharge2mePlay.recharge2me.R;

import Global.custom_Loading_Dialog.CustomToast;

public class Policies extends AppCompatActivity {

    TextView tv_RC,
             tv_TAC,
             tv_PP;

    CheckBox cb_PP,
             cb_TAC,
             cb_RCP;

    Button btn_Agree;

    CustomToast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policies);

        // TextView
        tv_RC = findViewById(R.id.RefundAndCancelation);
        tv_TAC = findViewById(R.id.tv_TAC);
        tv_PP = findViewById(R.id.tv_PP);

        // CheckBox
        cb_PP = findViewById(R.id.cb_PP);
        cb_TAC = findViewById(R.id.cb_TAC);
        cb_RCP = findViewById(R.id.cb_RCP);

        // Button
        btn_Agree = findViewById(R.id.btn_policiesAgree);

        // Custom
        toast = new CustomToast(this);

        try{
            String s = getIntent().getExtras().getString("Details");
            if(s.equals("fromMainUi")){
                btn_Agree.setVisibility(View.GONE);
                cb_PP.setVisibility(View.GONE);
                cb_TAC.setVisibility(View.GONE);
                cb_RCP.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
            Log.i("exp", e.getMessage());
            toast.showToast("Error! " + e.getMessage());
        }


        btn_Agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Agree();
            }
        });

        setRefundAndCancelation();
        setTermsAndCondiltions();
        setPrivacyPolicies();
    }

    private void setRefundAndCancelation(){
        String text = "<p>Our focus is complete customer satisfaction. In the event, if you are displeased with the services provided, we will refund back the money, provided the reasons are genuine and proved after investigation. Please read the fine prints of each deal before buying it, it provides all the details about the services or the product you purchase.</p>" +
                "<p>In case of dissatisfaction from our services, clients have the liberty to cancel their projects and request a refund from us. Our Policy for the cancellation and refund will be as follows:</p>" +
                " <h5> <font color=#000000>Cancellation Policy</font></h5>" +
                "<p>For Cancellations please contact the us via contact us link.</p>" +
                "<p>Requests received later than 2 business days prior to the end of the current service period will be treated as cancellation of services for the next service period.</p>" +
                "<h5><font color=#000000>Refund Policy</font></h5>" +
                "<p>We will try our best to create the suitable design concepts for our clients.</p>" +
                "<p>In case any client is not completely satisfied with our products we can provide a refund.</p>" +
                "<p>If paid by credit card, refunds will be issued to the original credit card provided at the time of purchase and in case of payment gateway name payments refund will be made to the same account.</p>";

        tv_RC.setText(Html.fromHtml(text));
    }
    private void setTermsAndCondiltions(){
        String text = "<p>The terms \"We\" / \"Us\" / \"Our\"/”Company” individually and collectively refer to Recharge2me and the terms \"Visitor” ”User” refer to the users</p>"+
                "<p>This page states the Terms and Conditions under which you (Visitor) may visit this website (“Website”). Please read this page carefully. If you do not accept the Terms and Conditions stated here, we would request you to exit this site. The business, any of its business divisions and / or its subsidiaries, associate companies or subsidiaries to subsidiaries or such other investment companies (in India or abroad) reserve their respective rights to revise these Terms and Conditions at any time by updating this posting. You should visit this page periodically to re-appraise yourself of the Terms and Conditions, because they are binding on all users of this Website.</p>"+
                "<h5><font color=#000000>USE OF CONTENT</font></h5>"+
                "<p>All logos, brands, marks headings, labels, names, signatures, numerals, shapes or any combinations thereof, appearing in this site, except as otherwise noted, are properties either owned, or used under licence, by the business and / or its associate entities who feature on this Website. The use of these properties or any other content on this site, except as provided in these terms and conditions or in the site content, is strictly prohibited.</p>"+
                "<p>You may not sell or modify the content of this Website  or reproduce, display, publicly perform, distribute, or otherwise use the materials in any way for any public or commercial purpose without the respective organisation’s or entity’s written permission.</p>"+
                "<h5><font color=#000000>ACCEPTABLE WEBSITE USE</font></h5>"+
                "<h6><font color=#000000>(A) Security Rules</font></h6>"+
                "<p>Visitors are prohibited from violating or attempting to violate the security of the Web site, including, without limitation, (1) accessing data not intended for such user or logging into a server or account which the user is not authorised to access, (2) attempting to probe, scan or test the vulnerability of a system or network or to breach security or authentication measures without proper authorisation, (3) attempting to interfere with service to any user, host or network, including, without limitation, via means of submitting a virus or \"Trojan horse\" to the Website, overloading, \"flooding\", \"mail bombing\" or \"crashing\", or (4) sending unsolicited electronic mail, including promotions and/or advertising of products or services. Violations of system or network security may result in civil or criminal liability. The business and / or its associate entities will have the right to investigate occurrences that they suspect as involving such violations and will have the right to involve, and cooperate with, law enforcement authorities in prosecuting users who are involved in such violations.</p>"+
                "<h6><font color=#000000>(B) General Rules</font></h6>"+
                "<p>Visitors may not use the Web Site in order to transmit, distribute, store or destroy material (a) that could constitute or encourage conduct that would be considered a criminal offence or violate any applicable law or regulation, (b) in a manner that will infringe the copyright, trademark, trade secret or other intellectual property rights of others or violate the privacy or publicity of other personal rights of others, or (c) that is libellous, defamatory, pornographic, profane, obscene, threatening, abusive or hateful.</p>"+
                "<h5><font color=#000000>INDEMNITY</font></h5>"+
                "<p>The User unilaterally agree to indemnify and hold harmless, without objection, the Company, its officers, directors, employees and agents from and against any claims, actions and/or demands and/or liabilities and/or losses and/or damages whatsoever arising from or resulting from their use of recharge2me.official@gmail.com or their breach of the terms </p>"+
                "<h5><font color=#000000>LIABILITY</font></h5>"+
                "<p>User agrees that neither Company nor its group companies, directors, officers or employee shall be liable for any direct or/and indirect or/and incidental or/and special or/and consequential or/and exemplary damages, resulting from the use or/and the inability to use the service or/and for cost of procurement of substitute goods or/and services or resulting from any goods or/and data or/and information or/and services purchased or/and obtained or/and messages received or/and transactions entered into through or/and from the service or/and resulting from unauthorized access to or/and alteration of user's transmissions or/and data or/and arising from any other matter relating to the service, including but not limited to, damages for loss of profits or/and use or/and data or other intangible, even if Company has been advised of the possibility of such damages. \n" +
                "User further agrees that Company shall not be liable for any damages arising from interruption, suspension or termination of service, including but not limited to direct or/and indirect or/and incidental or/and special consequential or/and exemplary damages, whether such interruption or/and suspension or/and termination was justified or not, negligent or intentional, inadvertent or advertent. \n" +
                "User agrees that Company shall not be responsible or liable to user, or anyone, for the statements or conduct of any third party of the service. In sum, in no event shall Company's total liability to the User for all damages or/and losses or/and causes of action exceed the amount paid by the User to Company, if any, that is related to the cause of action\n</p>"+
                "<h5><font color=#000000>DISCLAIMER OF CONSEQUENTIAL DAMAGES</font></h5>"+
                "<p>In no event shall Company or any parties, organizations or entities associated with the corporate brand name us or otherwise, mentioned at this Website be liable for any damages whatsoever (including, without limitations, incidental and consequential damages, lost profits, or damage to computer hardware or loss of data information or business interruption) resulting from the use or inability to use the Website and the Website material, whether based on warranty, contract, tort, or any other legal theory, and whether or not, such organization or entities were advised of the possibility of such damages.</p>";

                tv_TAC.setText(Html.fromHtml(text));
    }
    private void setPrivacyPolicies(){
        String text = "<p>The terms \"We\" / \"Us\" / \"Our\"/”Company” individually and collectively refer to Recharge2me and the terms \"You\" /\"Your\" / \"Yourself\" refer to the users. </p>" +
                "<p>This Privacy Policy is an electronic record in the form of an electronic contract formed under the information Technology Act, 2000 and the rules made thereunder and the amended provisions pertaining to electronic documents / records in various statutes as amended by the information Technology Act, 2000. This Privacy Policy does not require any physical, electronic or digital signature.</p>" +
                "<p>This Privacy Policy is a legally binding document between you and Recharge2me (both terms defined below). The terms of this Privacy Policy will be effective upon your acceptance of the same (directly or indirectly in electronic form, by clicking on the I accept tab or by use of the website or by other means) and will govern the relationship between you and Recharge2me for your use of the website “Application” (defined below).</p>" +
                "<p>This document is published and shall be construed in accordance with the provisions of the Information Technology (reasonable security practices and procedures and sensitive personal data of information) rules, 2011 under Information Technology Act, 2000; that require publishing of the Privacy Policy for collection, use, storage and transfer of sensitive personal data or information.</p>" +
                "<p>Please read this Privacy Policy carefully by using the Website, you indicate that you understand, agree and consent to this Privacy Policy. If you do not agree with the terms of this Privacy Policy, please do not use this Website</p>" +
                "<p>By providing us your Information or by making use of the facilities provided by the Website, You hereby consent to the collection, storage, processing and transfer of any or all of Your Personal Information and Non-Personal Information by us as specified under this Privacy Policy. You further agree that such collection, use, storage and transfer of Your Information shall not cause any loss or wrongful gain to you or any other person</p>" +

                "<h5><font color=#000000>USER INFORMATION </font></h5>"+
                "<p>To avail certain services on our Application, users are required to provide certain information for the registration process namely: - a) your name, b) email address, c) Number d) password etc. The Information as supplied by the users enables us to improve our app and provide you the most user-friendly experience.</p>" +
                "<p>All required information is service dependent and we may use the above said user information to, maintain, protect, and improve its services (including advertising services) and for developing new services</p>" +
                "<p>Such information will not be considered as sensitive if it is freely available and accessible in the public domain or is furnished under the Right to Information Act, 2005 or any other law for the time being in force.</p>" +

                "<h5><font color=#000000>COOKIES</font></h5>"+
                "<p>To improve the responsiveness of the sites for our users, we may use \"cookies\", or similar electronic tools to collect information to assign each visitor a unique, random number as a User Identification (User ID) to understand the user's individual interests using the Identified Computer. Unless you voluntarily identify yourself (through registration, for example), we will have no way of knowing who you are, even if we assign a cookie to your computer. The only personal information a cookie can contain is information you supply (an example of this is when you ask for our Personalised Horoscope). A cookie cannot read data off your hard drive. Our advertisers may also assign their own cookies to your browser (if you click on their ads), a process that we do not control</p>" +
                "<p>Our web servers automatically collect limited information about your computer's connection to the Internet, including your IP address, when you visit our site. (Your IP address is a number that lets computers attached to the Internet know where to send you data -- such as the web pages you view.) Your IP address does not identify you personally. We use this information to deliver our web pages to you upon request, to tailor our site to the interests of our users, to measure traffic within our site and let advertisers know the geographic locations from where our visitors come. </p>" +

                "<h5><font color=#000000>LINKS TO THE OTHER SITES</font></h5>"+
                "<p>Our policy discloses the privacy practices for our own Application only. Our app provides links to other websites also that are beyond our control. We shall in no way be responsible in way for your use of such sites. </p>" +

                "<h5><font color=#000000>INFORMATION SHARING</font></h5>"+
                "<p>We shares the sensitive personal information to any third party without obtaining the prior consent of the user in the following limited circumstances:</p>" +
                "<p><font color=#000000>(a)</font> When it is requested or required by law or by any court or governmental agency or authority to disclose, for the purpose of verification of identity, or for the prevention, detection, investigation including cyber incidents, or for prosecution and punishment of offences. These disclosures are made in good faith and belief that such disclosure is reasonably necessary for enforcing these Terms; for complying with the applicable laws and regulations</p>" +
                "<p><font color=#000000>(b)</font> We proposes to share such information within its group companies and officers and employees of such group companies for the purpose of processing personal information on its behalf. We also ensure that these recipients of such information agree to process such information based on our instructions and in compliance with this Privacy Policy and any other appropriate confidentiality and security measures.</p>" +

                "<h5><font color=#000000>INFORMATION SECURITY</font></h5>"+
                "<p>We take appropriate security measures to protect against unauthorized access to or unauthorized alteration, disclosure or destruction of data. These include internal reviews of our data collection, storage and processing practices and security measures, including appropriate encryption and physical security measures to guard against unauthorized access to systems where we store personal data.</p>"+
                "<p>All information gathered on our Application is securely stored within our controlled database. The database is stored on servers secured behind a firewall; access to the servers is password-protected and is strictly limited. However, as effective as our security measures are, no security system is impenetrable. We cannot guarantee the security of our database, nor can we guarantee that information you supply will not be intercepted while being transmitted to us over the Internet. And, of course, any information you include in a posting to the discussion areas is available to anyone with Internet access. </p>"+
                "<p>However the internet is an ever evolving medium. We may change our Privacy Policy from time to time to incorporate necessary future changes. Of course, our use of any information we gather will always be consistent with the policy under which the information was collected, regardless of what the new policy may be. </p>"+

                "<h5><font color=#000000>Grievance Redressal</font></h5>"+
                "<p>Redressal Mechanism: Any complaints, abuse or concerns with regards to content and or comment or breach of these terms shall be immediately informed to the designated Grievance Officer as mentioned below via in writing or through email signed with the electronic signature to recharge2me.help@gmail.com (\"Grievance Officer\"). </p>"+
                "<p>Mr. Mudssir Ahmed <font color=#000000>(Grievance Officer)</font></p>"+
                "<p>Company Name: Rechharge2me</p>"+
                "<p>Address: Shop No: 19, Block-B, Meedo Plaza, Rajpur Road, Dehradun (248001) </p>"+
                "<p>Email: recharge2me.official@gmail.com</p>"+
                "<p>Ph:  +91 8477055721</p>";

        tv_PP.setText(Html.fromHtml(text));
    }

    private void Agree(){
        if(check()){
                Intent intent = new Intent(this, Main_UserInterface.class);
                startActivity(intent);
        }else {
            toast.showToast("Please Check All the Boxes");
        }
    }

    private boolean check(){
        if(cb_RCP.isChecked() && cb_TAC.isChecked() && cb_PP.isChecked()){
            return true;
        }
        return false;
    }
}