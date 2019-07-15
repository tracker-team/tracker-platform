package com.platform.bootstrap;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Data;

@Data
public class TrackerConfiguration extends Configuration {
/*    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swagger;*/

    private DataSourceFactory trackerMasterDataSource=new DataSourceFactory();

}
