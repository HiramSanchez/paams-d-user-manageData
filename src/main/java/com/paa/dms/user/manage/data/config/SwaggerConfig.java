/*
 * Copyright (c) 2022, Bancoppel All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modifications are not
 * permitted.
 *
 * Bancoppel claims copyright in this computer program as an unpublished work.
 *
 * This program is a confidential trade secret and the property of Bancoppel.
 *
 * Use, examination, reproduction, disassembly, decompiling, transfer and/or disclosure to others of
 * all or any part of this software program are strictly prohibited except by express written
 * agreement with Bancoppel.
 *
 * This software is provided by the copyright holders and contributors "as is" and any express or
 * implied warranties, including, but not limited to, the implied warranties of merchantability and
 * fitness for a particular purpose are disclaimed. In no event shall the copyright owner or
 * contributors be liable for any direct, indirect, incidental, special, exemplary, or consequential
 * damages (including, but not limited to, procurement of substitute goods or services; Loss of use,
 * data, or profits; Or business interruption) however caused and on any theory of liability,
 * whether in contract, strict liability, or tort (including negligence or otherwise) arising in any
 * way out of the use of this software, even if advised of the possibility of such damage.
 *
 * Developed by Bancoppel.
 */

package com.paa.dms.user.manage.data.config;

import com.paa.dms.user.manage.data.constants.APIConstants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger-UI Config.
 */
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
  @Autowired
  private APIConstants apiConstants;
  /**
   * Bean to customise swagger.
   */
  @Bean
  public OpenApiCustomizer productApi() {
    Api api = new Api();
    api.customise(new OpenAPI());
    return api;
  }

  /**
   * Internal class swagger-ui customization.
   */
  class Api implements OpenApiCustomizer {

    @Override
    public void customise(OpenAPI openApi) {
      openApi.info(apiInfo());
    }

    /**
     * Builder to fill up API's info.
     * @return Object containing API's information.
     */
    private Info apiInfo() {
      Contact contact = new Contact();
      contact.setName(apiConstants.getDEV_NAME());
      contact.setUrl(apiConstants.getDEV_WEB());
      contact.setEmail(apiConstants.getDEV_EMAIL());

      Info info = new Info();
      info.setTitle(apiConstants.getSERVICE_NAME());
      info.setDescription(apiConstants.getSERVICE_DESCRIPTION());
      info.setVersion(apiConstants.getSERVICE_VERSION());
      info.setContact(contact);

      return info;
    }
  }
}
