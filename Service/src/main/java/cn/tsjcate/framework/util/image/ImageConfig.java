package cn.tsjcate.framework.util.image;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * ImageConfig
 */
@Component
public class ImageConfig {
	
	/**
	 * key:模块名
	 * value:模块配置
	 */
	//存放module.namekey的值
	private final Map<String, ModuleConfig> imageModuleMap = new HashMap<>();
	
	private ResourceBundle bundle;
	
	/**
	 * 初始化
	 * 加载配置文件中的信息将信息转换为java对象
	 * 成员变量有module,namekey,以及存储大小的map字段
	 */
	@PostConstruct
	public void init() {
		bundle = ResourceBundle.getBundle("image", Locale.SIMPLIFIED_CHINESE);
		String[] imageModules = bundle.getString("modules").split(",");
		ModuleConfig moduleConfig;
		Enumeration<String> enu = bundle.getKeys();//枚举所有的properties
		while (enu.hasMoreElements()) {//循环遍历所有properties
			String key = enu.nextElement();
			for (String module: imageModules) {//循环遍历所有的modules
				if (key.equals(module + ".nameKey")){
					String moduleKey = bundle.getString(key);//得到namekey的值
					if (imageModuleMap.containsKey(module)) {
						imageModuleMap.get(module).setModuleKey(moduleKey);
					} else {
						moduleConfig = new ModuleConfig();
						moduleConfig.setModule(module);
						moduleConfig.setModuleKey(moduleKey);
						imageModuleMap.put(module, moduleConfig);
					}
				} else if (key.matches("^" + module + ".[0-9]+$")) {
					if (imageModuleMap.containsKey(module)) {
						imageModuleMap.get(module).getImageSizeMap().put(Integer.valueOf(key.split("\\.")[1]), bundle.getString(key).split(":")[1]);
					} else {
						moduleConfig = new ModuleConfig();
						moduleConfig.setModule(module);
						moduleConfig.getImageSizeMap().put(Integer.valueOf(key.split("\\.")[1]), bundle.getString(key).split(":")[1]);
						imageModuleMap.put(module, moduleConfig);
					}
				}
			}
		}
	}
	
	public String getImageModuleKey(String moduleName){
		return imageModuleMap.get(moduleName).getModuleKey();
	}
	
	public String getDestinationBasePath(){
		return bundle.getString("destinationBasePath");
	}
	
	public String getDetailDestinationBasePath(){
		return bundle.getString("detailDestinationBasePath");
	}
	
	public String getSourceBasePath(){
		return bundle.getString("sourceBasePath");
	}
	
	public String getDetailSourceBasePath(){
		return bundle.getString("detailSourceBasePath");
	}
	
	public String getImageSize(String moduleName, Integer imageIndex) {
		return imageModuleMap.get(moduleName).getImageSizeMap().get(imageIndex);
	}

	/**
	 * 得到大小的key,即为1，2,3,4
	 * deal.1=size\:202*218
	   deal.2=size\:95*95
	   deal.3=size\:309*340
	    deal.4=size\:238*170
	 * @param moduleName
	 * @return
	 */
	public List<Integer> getImageIndexList(String moduleName) {
		return new ArrayList<>(imageModuleMap.get(moduleName).getImageSizeMap().keySet());
	}
	
	public List<String> getImageSizeList(String moduleName) {
		return new ArrayList<>(imageModuleMap.get(moduleName).getImageSizeMap().values());
	}
	
}

class ModuleConfig {
	
	private String module;//属性名
	
	private String moduleKey;//module的key值
	
	private Map<Integer, String> imageSizeMap = new HashMap<>();//存放大小的map

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getModuleKey() {
		return moduleKey;
	}

	public void setModuleKey(String moduleKey) {
		this.moduleKey = moduleKey;
	}

	public Map<Integer, String> getImageSizeMap() {
		return imageSizeMap;
	}

	public void setImageSizeMap(Map<Integer, String> imageSizeMap) {
		this.imageSizeMap = imageSizeMap;
	}
	
}
