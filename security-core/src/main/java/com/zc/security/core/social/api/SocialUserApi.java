package com.zc.security.core.social.api;


import org.springframework.social.connect.ConnectionValues;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/12 下午1:49
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public interface SocialUserApi {

    SocialUser getUserInfo(String openId,String accessToken);


     class SocialUser {

         private String providerUserId;

         private String displayName;

         private String profileUrl;

         private String imageUrl;

         public String getProviderUserId() {
             return providerUserId;
         }

         public void setProviderUserId(String providerUserId) {
             this.providerUserId = providerUserId;
         }

         public String getDisplayName() {
             return displayName;
         }

         public void setDisplayName(String displayName) {
             this.displayName = displayName;
         }

         public String getProfileUrl() {
             return profileUrl;
         }

         public void setProfileUrl(String profileUrl) {
             this.profileUrl = profileUrl;
         }

         public String getImageUrl() {
             return imageUrl;
         }

         public void setImageUrl(String imageUrl) {
             this.imageUrl = imageUrl;
         }
     }
}
