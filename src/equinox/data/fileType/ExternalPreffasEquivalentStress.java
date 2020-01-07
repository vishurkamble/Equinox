/*
 * Copyright 2018 Murat Artim (muratartim@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package equinox.data.fileType;

import equinox.data.ActionHandler;
import equinox.font.IconicFont;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 * Class for external Preffas equivalent stress.
 *
 * @author Murat Artim
 * @date Mar 11, 2015
 * @time 10:43:03 AM
 */
public class ExternalPreffasEquivalentStress extends SpectrumItem {

	/** Omission level. */
	private final double omissionLevel_;

	/** Material name. */
	private final String materialName_;

	/**
	 * Creates external Preffas equivalent stress item.
	 *
	 * @param name
	 *            Name of equivalent stress.
	 * @param id
	 *            ID of item.
	 * @param omissionLevel
	 *            The omission level. -1 should be given if no omission is applied.
	 * @param materialName
	 *            Material name.
	 */
	public ExternalPreffasEquivalentStress(String name, int id, double omissionLevel, String materialName) {

		// create spectrum item
		super(name, id);
		omissionLevel_ = omissionLevel;
		materialName_ = materialName;

		// create icon label
		Label iconLabel = new Label("\uf192");
		iconLabel.getStylesheets().add(IconicFont.FONTAWESOME.getStyleSheet());
		setGraphic(iconLabel);
	}

	/**
	 * Returns the omission level. Note that, this will return -1 if no omission is applied.
	 *
	 * @return The omission level.
	 */
	public double getOmissionLevel() {
		return omissionLevel_;
	}

	/**
	 * Returns the material name.
	 *
	 * @return The material name.
	 */
	public String getMaterialName() {
		return materialName_;
	}

	@Override
	public ExternalStressSequence getParentItem() {
		return (ExternalStressSequence) getParent();
	}

	@Override
	public IconicFont getIconFont() {
		return IconicFont.FONTAWESOME;
	}

	/**
	 * Creates and returns context menu for ANA files.
	 *
	 * @param multipleSelection
	 *            True if multiple ANA files are selected.
	 * @param handler
	 *            Action handler.
	 * @param is3dEnabled
	 *            True if 3D viewer is enabled.
	 * @return Context menu.
	 */
	public static ContextMenu createContextMenu(boolean multipleSelection, ActionHandler handler, boolean is3dEnabled) {

		// create menu
		ContextMenu contextMenu = new ContextMenu();

		// plot level crossing
		Label plotIcon = new Label("\ue901");
		plotIcon.getStylesheets().add(IconicFont.CUSTOM.getStyleSheet());
		MenuItem levelCrossing = new MenuItem("Plot Level Crossings", plotIcon);
		levelCrossing.setId("plotExternalLevelCrossing");
		levelCrossing.setOnAction(handler);
		contextMenu.getItems().add(levelCrossing);

		// plot rainflow histogram
		if (!multipleSelection) {
			Label statisticsIcon = new Label("\uf080");
			statisticsIcon.getStylesheets().add(IconicFont.FONTAWESOME.getStyleSheet());
			MenuItem histogram = new MenuItem("Plot Rainflow Histogram", statisticsIcon);
			histogram.setId("plotHistogram");
			histogram.setOnAction(handler);
			contextMenu.getItems().add(histogram);
		}

		// plot 3D rainflow histogram
		if (!multipleSelection) {
			Label statisticsIcon = new Label("\ueb89");
			statisticsIcon.getStylesheets().add(IconicFont.ICOMOON.getStyleSheet());
			MenuItem histogram = new MenuItem("Plot 3D Rainflow Histogram", statisticsIcon);
			histogram.setId("plot3DHistogram");
			histogram.setOnAction(handler);
			histogram.setDisable(!is3dEnabled);
			contextMenu.getItems().add(histogram);
		}

		// show analysis output file
		if (!multipleSelection) {
			final Label outputIcon = new Label("\ueec6");
			outputIcon.getStylesheets().add(IconicFont.ICOMOON.getStyleSheet());
			final MenuItem output = new MenuItem("Show Analysis Output File", outputIcon);
			output.setId("showAnalysisOutputFile");
			output.setOnAction(handler);
			contextMenu.getItems().add(output);
		}

		// compare equivalent stresses
		if (multipleSelection) {
			Label statisticsIcon = new Label("\uf24e");
			statisticsIcon.getStylesheets().add(IconicFont.FONTAWESOME.getStyleSheet());
			MenuItem compare = new MenuItem("Compare Equivalent Stresses", statisticsIcon);
			compare.setId("compareEquivalentStress");
			compare.setOnAction(handler);
			contextMenu.getItems().add(compare);
		}

		// add separator
		contextMenu.getItems().add(new SeparatorMenuItem());

		// multiple selection
		if (multipleSelection) {

			// generate life factors
			Label generateLifeFactorsIcon = new Label("\ueb84");
			generateLifeFactorsIcon.getStylesheets().add(IconicFont.ICOMOON.getStyleSheet());
			MenuItem generateLifeFactors = new MenuItem("Generate Life/Adjustment Factors", generateLifeFactorsIcon);
			generateLifeFactors.setId("generateLifeFactor");
			generateLifeFactors.setOnAction(handler);
			contextMenu.getItems().add(generateLifeFactors);

			// generate equivalent stress ratios
			Label generateStressRatiosIcon = new Label("\ue902");
			generateStressRatiosIcon.getStylesheets().add(IconicFont.CUSTOM.getStyleSheet());
			MenuItem generateStressRatios = new MenuItem("Generate Equivalent Stress Ratios", generateStressRatiosIcon);
			generateStressRatios.setId("generateStressRatio");
			generateStressRatios.setOnAction(handler);
			contextMenu.getItems().add(generateStressRatios);

			// add separator
			contextMenu.getItems().add(new SeparatorMenuItem());
		}

		// create share menu
		Label shareIcon = new Label("\uf064");
		shareIcon.getStylesheets().add(IconicFont.FONTAWESOME.getStyleSheet());
		Menu share = new Menu("Share...", shareIcon);
		contextMenu.getItems().add(share);

		// share equivalent stress
		Label eqStressIcon = new Label("\uedaf");
		eqStressIcon.getStylesheets().add(IconicFont.ICOMOON.getStyleSheet());
		MenuItem shareStress = new MenuItem(multipleSelection ? "Equivalent Stresses" : "Equivalent Stress", eqStressIcon);
		shareStress.setId("shareEquivalentStress");
		shareStress.setOnAction(handler);
		share.getItems().add(shareStress);

		// share analysis output file
		if (!multipleSelection) {
			final Label outputIcon = new Label("\ueec6");
			outputIcon.getStylesheets().add(IconicFont.ICOMOON.getStyleSheet());
			final MenuItem shareOutput = new MenuItem("Analysis Output File", outputIcon);
			shareOutput.setId("share");
			shareOutput.setOnAction(handler);
			share.getItems().add(shareOutput);
		}

		// multiple selection
		if (multipleSelection) {

			// share life factors
			final Label generateLifeFactorsIcon = new Label("\ueb84");
			generateLifeFactorsIcon.getStylesheets().add(IconicFont.ICOMOON.getStyleSheet());
			MenuItem shareLifeFactors = new MenuItem("Life/Adjustment Factors", generateLifeFactorsIcon);
			shareLifeFactors.setId("shareLifeFactors");
			shareLifeFactors.setOnAction(handler);
			share.getItems().add(shareLifeFactors);

			// share equivalent stress ratios
			final Label generateStressRatiosIcon = new Label("\ue902");
			generateStressRatiosIcon.getStylesheets().add(IconicFont.CUSTOM.getStyleSheet());
			MenuItem shareStressRatios = new MenuItem("Equivalent Stress Ratios", generateStressRatiosIcon);
			shareStressRatios.setId("shareStressRatios");
			shareStressRatios.setOnAction(handler);
			share.getItems().add(shareStressRatios);
		}

		// share rainflow cycles
		if (!multipleSelection) {
			final Label statisticsIcon = new Label("\uf080");
			statisticsIcon.getStylesheets().add(IconicFont.FONTAWESOME.getStyleSheet());
			MenuItem shareCycles = new MenuItem("Rainflow Cycle Info", statisticsIcon);
			shareCycles.setId("shareRainflowCycle");
			shareCycles.setOnAction(handler);
			share.getItems().add(shareCycles);
		}

		// create save menu
		Label saveIcon = new Label("\uf0c7");
		saveIcon.getStylesheets().add(IconicFont.FONTAWESOME.getStyleSheet());
		Menu save = new Menu("Save...", saveIcon);
		contextMenu.getItems().add(save);

		// save equivalent stress
		Label eqStressIcon2 = new Label("\uedaf");
		eqStressIcon2.getStylesheets().add(IconicFont.ICOMOON.getStyleSheet());
		MenuItem saveStress = new MenuItem(multipleSelection ? "Equivalent Stresses" : "Equivalent Stress", eqStressIcon2);
		saveStress.setId("saveEqStress");
		saveStress.setOnAction(handler);
		save.getItems().add(saveStress);

		// save analysis output file
		final Label outputIcon = new Label("\ueec6");
		outputIcon.getStylesheets().add(IconicFont.ICOMOON.getStyleSheet());
		final MenuItem outputFile = new MenuItem(multipleSelection ? "Analysis Output Files" : "Analysis Output File", outputIcon);
		outputFile.setId("saveAnalysisOutputFile");
		outputFile.setOnAction(handler);
		save.getItems().add(outputFile);

		// multiple selection
		if (multipleSelection) {

			// save life factors
			final Label generateLifeFactorsIcon = new Label("\ueb84");
			generateLifeFactorsIcon.getStylesheets().add(IconicFont.ICOMOON.getStyleSheet());
			MenuItem saveLifeFactors = new MenuItem("Life/Adjustment Factors", generateLifeFactorsIcon);
			saveLifeFactors.setId("saveLifeFactors");
			saveLifeFactors.setOnAction(handler);
			save.getItems().add(saveLifeFactors);

			// save equivalent stress ratios
			final Label generateStressRatiosIcon = new Label("\ue902");
			generateStressRatiosIcon.getStylesheets().add(IconicFont.CUSTOM.getStyleSheet());
			MenuItem saveStressRatios = new MenuItem("Equivalent Stress Ratios", generateStressRatiosIcon);
			saveStressRatios.setId("saveStressRatios");
			saveStressRatios.setOnAction(handler);
			save.getItems().add(saveStressRatios);
		}

		// save rainflow cycles
		final Label statisticsIcon = new Label("\uf080");
		statisticsIcon.getStylesheets().add(IconicFont.FONTAWESOME.getStyleSheet());
		MenuItem saveRainflow = new MenuItem("Rainflow Cycle Info", statisticsIcon);
		saveRainflow.setId("saveRainflow");
		saveRainflow.setOnAction(handler);
		save.getItems().add(saveRainflow);

		// find
		contextMenu.getItems().add(new SeparatorMenuItem());
		Label findIcon = new Label("\uf002");
		findIcon.getStylesheets().add(IconicFont.FONTAWESOME.getStyleSheet());
		MenuItem find = new MenuItem("Find Similar Files", findIcon);
		find.setId("find");
		find.setOnAction(handler);
		contextMenu.getItems().add(find);

		// rename
		Label renameIcon = new Label("\uf246");
		renameIcon.getStylesheets().add(IconicFont.FONTAWESOME.getStyleSheet());
		MenuItem rename = new MenuItem("Rename...", renameIcon);
		rename.setId("rename");
		rename.setOnAction(handler);
		contextMenu.getItems().add(rename);

		// delete
		contextMenu.getItems().add(new SeparatorMenuItem());
		Label deleteIcon = new Label("\uf014");
		deleteIcon.getStylesheets().add(IconicFont.FONTAWESOME.getStyleSheet());
		MenuItem remove = new MenuItem("Delete", deleteIcon);
		remove.setId("delete");
		remove.setOnAction(handler);
		contextMenu.getItems().add(remove);

		// return menu
		return contextMenu;
	}
}
