package android.app;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.*;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import com.github.unidbg.linux.android.dvm.DvmObject;
import com.github.unidbg.linux.android.dvm.VM;

import java.util.List;

public class ApplicationObject extends DvmObject<String> {

    public ApplicationObject(VM vm, String value) {
        super(vm.resolveClass("android/app/Application"), value);
    }

    public PackageManager getPackageManager(){
        return new PackageManager() {
            @Override
            public PackageInfo getPackageInfo(String s, int i) throws NameNotFoundException {
                return null;
            }

            @Override
            public String[] currentToCanonicalPackageNames(String[] strings) {
                return new String[0];
            }

            @Override
            public String[] canonicalToCurrentPackageNames(String[] strings) {
                return new String[0];
            }

            @Override
            public Intent getLaunchIntentForPackage(String s) {
                return null;
            }

            @Override
            public int[] getPackageGids(String s) throws NameNotFoundException {
                return new int[0];
            }

            @Override
            public PermissionInfo getPermissionInfo(String s, int i) throws NameNotFoundException {
                return null;
            }

            @Override
            public List<PermissionInfo> queryPermissionsByGroup(String s, int i) throws NameNotFoundException {
                return null;
            }

            @Override
            public PermissionGroupInfo getPermissionGroupInfo(String s, int i) throws NameNotFoundException {
                return null;
            }

            @Override
            public List<PermissionGroupInfo> getAllPermissionGroups(int i) {
                return null;
            }

            @Override
            public ApplicationInfo getApplicationInfo(String s, int i) throws NameNotFoundException {
                return null;
            }

            @Override
            public ActivityInfo getActivityInfo(ComponentName componentName, int i) throws NameNotFoundException {
                return null;
            }

            @Override
            public ActivityInfo getReceiverInfo(ComponentName componentName, int i) throws NameNotFoundException {
                return null;
            }

            @Override
            public ServiceInfo getServiceInfo(ComponentName componentName, int i) throws NameNotFoundException {
                return null;
            }

            @Override
            public ProviderInfo getProviderInfo(ComponentName componentName, int i) throws NameNotFoundException {
                return null;
            }

            @Override
            public List<PackageInfo> getInstalledPackages(int i) {
                return null;
            }

            @Override
            public int checkPermission(String s, String s1) {
                return 0;
            }

            @Override
            public boolean addPermission(PermissionInfo permissionInfo) {
                return false;
            }

            @Override
            public boolean addPermissionAsync(PermissionInfo permissionInfo) {
                return false;
            }

            @Override
            public void removePermission(String s) {

            }

            @Override
            public int checkSignatures(String s, String s1) {
                return 0;
            }

            @Override
            public int checkSignatures(int i, int i1) {
                return 0;
            }

            @Override
            public String[] getPackagesForUid(int i) {
                return new String[0];
            }

            @Override
            public String getNameForUid(int i) {
                return null;
            }

            @Override
            public List<ApplicationInfo> getInstalledApplications(int i) {
                return null;
            }

            @Override
            public String[] getSystemSharedLibraryNames() {
                return new String[0];
            }

            @Override
            public FeatureInfo[] getSystemAvailableFeatures() {
                return new FeatureInfo[0];
            }

            @Override
            public boolean hasSystemFeature(String s) {
                return false;
            }

            @Override
            public ResolveInfo resolveActivity(Intent intent, int i) {
                return null;
            }

            @Override
            public List<ResolveInfo> queryIntentActivities(Intent intent, int i) {
                return null;
            }

            @Override
            public List<ResolveInfo> queryIntentActivityOptions(ComponentName componentName, Intent[] intents, Intent intent, int i) {
                return null;
            }

            @Override
            public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int i) {
                return null;
            }

            @Override
            public ResolveInfo resolveService(Intent intent, int i) {
                return null;
            }

            @Override
            public List<ResolveInfo> queryIntentServices(Intent intent, int i) {
                return null;
            }

            @Override
            public ProviderInfo resolveContentProvider(String s, int i) {
                return null;
            }

            @Override
            public List<ProviderInfo> queryContentProviders(String s, int i, int i1) {
                return null;
            }

            @Override
            public InstrumentationInfo getInstrumentationInfo(ComponentName componentName, int i) throws NameNotFoundException {
                return null;
            }

            @Override
            public List<InstrumentationInfo> queryInstrumentation(String s, int i) {
                return null;
            }

            @Override
            public Drawable getDrawable(String s, int i, ApplicationInfo applicationInfo) {
                return null;
            }

            @Override
            public Drawable getActivityIcon(ComponentName componentName) throws NameNotFoundException {
                return null;
            }

            @Override
            public Drawable getActivityIcon(Intent intent) throws NameNotFoundException {
                return null;
            }

            @Override
            public Drawable getDefaultActivityIcon() {
                return null;
            }

            @Override
            public Drawable getApplicationIcon(ApplicationInfo applicationInfo) {
                return null;
            }

            @Override
            public Drawable getApplicationIcon(String s) throws NameNotFoundException {
                return null;
            }

            @Override
            public Drawable getActivityLogo(ComponentName componentName) throws NameNotFoundException {
                return null;
            }

            @Override
            public Drawable getActivityLogo(Intent intent) throws NameNotFoundException {
                return null;
            }

            @Override
            public Drawable getApplicationLogo(ApplicationInfo applicationInfo) {
                return null;
            }

            @Override
            public Drawable getApplicationLogo(String s) throws NameNotFoundException {
                return null;
            }

            @Override
            public CharSequence getText(String s, int i, ApplicationInfo applicationInfo) {
                return null;
            }

            @Override
            public XmlResourceParser getXml(String s, int i, ApplicationInfo applicationInfo) {
                return null;
            }

            @Override
            public CharSequence getApplicationLabel(ApplicationInfo applicationInfo) {
                return null;
            }

            @Override
            public Resources getResourcesForActivity(ComponentName componentName) throws NameNotFoundException {
                return null;
            }

            @Override
            public Resources getResourcesForApplication(ApplicationInfo applicationInfo) throws NameNotFoundException {
                return null;
            }

            @Override
            public Resources getResourcesForApplication(String s) throws NameNotFoundException {
                return null;
            }

            @Override
            public void verifyPendingInstall(int i, int i1) {

            }

            @Override
            public void setInstallerPackageName(String s, String s1) {

            }

            @Override
            public String getInstallerPackageName(String s) {
                return null;
            }

            @Override
            public void addPackageToPreferred(String s) {

            }

            @Override
            public void removePackageFromPreferred(String s) {

            }

            @Override
            public List<PackageInfo> getPreferredPackages(int i) {
                return null;
            }

            @Override
            public void addPreferredActivity(IntentFilter intentFilter, int i, ComponentName[] componentNames, ComponentName componentName) {

            }

            @Override
            public void clearPackagePreferredActivities(String s) {

            }

            @Override
            public int getPreferredActivities(List<IntentFilter> list, List<ComponentName> list1, String s) {
                return 0;
            }

            @Override
            public void setComponentEnabledSetting(ComponentName componentName, int i, int i1) {

            }

            @Override
            public int getComponentEnabledSetting(ComponentName componentName) {
                return 0;
            }

            @Override
            public void setApplicationEnabledSetting(String s, int i, int i1) {

            }

            @Override
            public int getApplicationEnabledSetting(String s) {
                return 0;
            }

            @Override
            public boolean isSafeMode() {
                return false;
            }
        };
    }
}
