package com.jew.plugin.activeRecord.generator;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

public class Generator {
	/**
	 * stoles all the meta info
	 */
	private MetaBuilder metaBuilder;
	private BaseModelGenerator baseModelGenerator;
	private ModelGenerator modelGenerator;
	private MappingKitGenerator mappingGenerator;

	public Generator() {

	}

	public Generator(DataSource dataSource, String baseModelPackageDir, String baseModelOutputDir,
			String modelPackageDir, String modelOutputDir) {
		this.metaBuilder = new MetaBuilder(dataSource);
		baseModelGenerator = new BaseModelGenerator(baseModelPackageDir, baseModelOutputDir);
		modelGenerator = new ModelGenerator(modelOutputDir, modelPackageDir);
		mappingGenerator = new MappingKitGenerator(modelOutputDir, modelPackageDir);
	}

	public void generator() {
		long start = System.currentTimeMillis();
		List<TableMeta> tabMetaList = metaBuilder.build();
		baseModelGenerator.generate(tabMetaList);
		modelGenerator.generate(tabMetaList);
		mappingGenerator.generate(tabMetaList);
		long end = System.currentTimeMillis();
		System.out.println(String.format("%d sec", TimeUnit.MILLISECONDS.toSeconds(end - start)
				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(end - start))));
	}

	public void setRemovedTablePrefix(String... prefixs) {
		metaBuilder.setRemovedTablePrefix(prefixs);
	}

	public void setExcluedTables(String... tables) {
		metaBuilder.addExcludedTable(tables);
	}

}
