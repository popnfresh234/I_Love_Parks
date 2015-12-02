package alexander.dmtaiwan.com.parks.Bus;

/**
 * Created by lenovo on 12/2/2015.
 */
public class SettingsEvent{

    private String mSettingKey;

    public SettingsEvent(String settingsKey) {
        this.mSettingKey = settingsKey;
    }

    public String getSettingKey() {
        return mSettingKey;
    }
}
