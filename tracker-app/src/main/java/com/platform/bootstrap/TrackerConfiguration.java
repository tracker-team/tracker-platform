package com.platform.bootstrap;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.Data;

@Data
public class TrackerConfiguration extends Configuration {
    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swagger;

    private DataSourceFactory trackerMasterDataSource=new DataSourceFactory();

}
