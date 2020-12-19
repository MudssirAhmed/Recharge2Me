package recahrge.DataTypes.rechargeDataTypes;

import java.util.List;

public class Pay2All_providers {

    private String title;
    private List<Services> services;
    private List<Providers> providers;

    public String getTitle() {
        return title;
    }

    public List<Services> getServices() {
        return services;
    }

    public List<Providers> getProviders() {
        return providers;
    }

    private static class Services{

    }

    public static class Providers{

        //            "id": 1,
        //            "provider_name": "AIRTEL",
        //            "service_id": 1,
        //            "description": "Airtel",
        //            "status": 1,
        //            "icon": null

        String id;
        String provider_name;
        String service_id;
        String description;
        String status;
        String icon;

        public String getId() {
            return id;
        }

        public String getProvider_name() {
            return provider_name;
        }

        public String getService_id() {
            return service_id;
        }

        public String getDescription() {
            return description;
        }

        public String getStatus() {
            return status;
        }

        public String getIcon() {
            return icon;
        }
    }

}
