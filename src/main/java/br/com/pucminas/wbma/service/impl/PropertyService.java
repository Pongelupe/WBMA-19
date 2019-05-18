package br.com.pucminas.wbma.service.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import br.com.pucminas.wbma.service.BaseService;
import br.com.pucminas.wbma.service.IPropertyService;


public class PropertyService implements IPropertyService {

	private static final String WBMA_IT_PROPERTIES = "/wbma.properties";

	private Properties prop;

	public PropertyService() {
		try {
			prop = new Properties();
			FileInputStream fileInputStream = new FileInputStream(PropertyService.class.getResource(WBMA_IT_PROPERTIES).getFile());
			prop.load(fileInputStream);
			fileInputStream.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("Error loading " + WBMA_IT_PROPERTIES + " file");
		}
	}

	public Properties getProp() {
		return prop;
	}

	@Override
	public Optional<String> getProp(String propKey) {
		return Optional.ofNullable(this.prop.get(propKey))
				.map(Object::toString);
	}

	@Override
	public void setProp(String propKey, String value) {
		this.prop.put(propKey, value);
	}

	@Override
	public void setPropEager(String propKey, String value) {
		try {
			this.setProp(propKey, value);
			commitChanges();
		} catch (IOException e) {
			this.setProp(propKey, value);
		}
	}

	@Override
	public void commitChanges() throws IOException {
		this.prop.store(new FileOutputStream(BaseService.class.getResource(WBMA_IT_PROPERTIES).getFile()), null);
	}

}
