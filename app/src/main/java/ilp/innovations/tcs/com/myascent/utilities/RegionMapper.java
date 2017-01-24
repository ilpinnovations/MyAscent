package ilp.innovations.tcs.com.myascent.utilities;

/**
 * Created by 1115394 on 12/29/2016.
 */
public class RegionMapper {
    public static final String KOCHI = "KOCHI";
    public static final String TRIVANDRUM = "TRIVANDRUM";
    public static final String HYDERABAD = "HYDERABAD";
    public static final String CHENNAI = "CHENNAI";
    public static final String BANGALORE = "BANGALORE";
    public static final String NOIDA = "NOIDA";
    public static final String GURGAON = "GURGAON";
    public static final String AHMEDABAD = "AHMEDABAD";
    public static final String KOLKATA = "KOLKATA";
    public static final String BHUBANESHWAR = "BHUBANESHWAR";
    public static final String MUMBAI = "MUMBAI";
    public static final String PUNE = "PUNE";
    public static final String LUCKNOW = "LUCKNOW";
    public static final String OVERSEAS = "OVERSEAS";
    public static final String LATAM = "LATAM";
    public static final String BLANK = "";
    public static final String CHENNAI2 = "CHENNAI2";

    public static final int KOCHI_ID = 1;
    public static final int TRIVANDRUM_ID = 2;
    public static final int HYDERABAD_ID = 3;
    public static final int CHENNAI_ID = 4;
    public static final int BANGALORE_ID = 5;
    public static final int NOIDA_ID = 6;
    public static final int GURGAON_ID = 7;
    public static final int AHMEDABAD_ID = 8;
    public static final int KOLKATA_ID = 9;
    public static final int BHUBANESHWAR_ID = 10;
    public static final int MUMBAI_ID = 11;
    public static final int PUNE_ID= 12;
    public static final int LUCKNOW_ID = 13;
    public static final int OVERSEAS_ID = 14;
    public static final int LATAM_ID = 15;
    public static final int BLANK_ID = 16;
    public static final int CHENNAI2_ID = 17;

    public static int getRegionId(String region){
        region = region.toUpperCase();

        switch (region){
            case KOCHI:
                return KOCHI_ID;

            case TRIVANDRUM:
                return TRIVANDRUM_ID;

            case HYDERABAD:
                return HYDERABAD_ID;

            case CHENNAI:
                return CHENNAI_ID;

            case BANGALORE:
                return BANGALORE_ID;

            case NOIDA:
                return NOIDA_ID;

            case GURGAON:
                return GURGAON_ID;

            case AHMEDABAD:
                return AHMEDABAD_ID;

            case KOLKATA:
                return KOLKATA_ID;

            case BHUBANESHWAR:
                return BHUBANESHWAR_ID;

            case MUMBAI:
                return MUMBAI_ID;

            case PUNE:
                return PUNE_ID;

            case LUCKNOW:
                return LUCKNOW_ID;

            case OVERSEAS:
                return OVERSEAS_ID;

            case LATAM:
                return LATAM_ID;

            case BLANK:
                return BLANK_ID;

            case CHENNAI2:
                return CHENNAI2_ID;

            default:
                return -1;
        }
    }

}
