package com.sdg.compliancepro.service.settings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.sdg.compliancepro.common.exception.ApplicationException;
import com.sdg.compliancepro.dto.request.AssetCategoryRequest;
import com.sdg.compliancepro.dto.response.AssetCategoryResponse;
import com.sdg.compliancepro.entity.AssetCategory;
import com.sdg.compliancepro.repository.AssetCategoryRepo;
import com.sdg.compliancepro.util.MapperConvertor;


@RunWith(MockitoJUnitRunner.class)
public class TestAssetCategoriesServ {

	@InjectMocks
	AssetCategoriesServImpl assetCategoriesServ;
	@Mock
	OrganizationServ registrationService;
	@Mock
	AssetCategoryRepo assetCatgRepo;

	@Mock
	MapperConvertor convertor;
	
	@Test
	public void saveAssetCategorySucess() throws ApplicationException {
		AssetCategory assetCategory = new AssetCategory();

		assetCategory.setName("AssetCatName");
		assetCategory.setDescription("AssetCatDesc");
		when(registrationService.isValidOrganization(Integer.valueOf(0))).thenReturn(true);
		when(assetCatgRepo.save(assetCategory)).thenReturn(assetCategory);

		assertEquals(assetCategory, assetCatgRepo.save(assetCategory));

	}

	@Test
	public void getAllAssetCategorySucess() throws ApplicationException {
		List<AssetCategory> list = new ArrayList<>();
		AssetCategory assetCategory = new AssetCategory();
		assetCategory.setName("AssetCatName");
		assetCategory.setDescription("AssetCatDesc");

		list.add(assetCategory);

		when(assetCatgRepo.findAll()).thenReturn(list);
		when(registrationService.isValidOrganization(Integer.valueOf(0))).thenReturn(true);
		// test
		List<AssetCategoryResponse> assetCategoryList = assetCategoriesServ.getAssetCategory(Integer.valueOf(0));

		assertEquals(1, assetCategoryList.size());
		verify(assetCatgRepo, times(1)).findAll();
		// verify(assetCategoriesServ, times(1)).getAssetCategory();
	}

	@Test
	public void getAssetCategoryByIdSucessId() throws ApplicationException {
		AssetCategory assetCategory = new AssetCategory();
		assetCategory.setId(1);
		assetCategory.setName("AssetCatName");
		assetCategory.setDescription("AssetCatDesc");
		when(assetCatgRepo.findById(1)).thenReturn(Optional.of(assetCategory));
		// when(Optional.of(assetCategory).isPresent()).thenReturn(true);
		// when(Optional.of(assetCategory).get()).thenReturn(assetCategory);
		when(registrationService.isValidOrganization(Integer.valueOf(0))).thenReturn(true);
		AssetCategoryResponse assetCategorydata = assetCategoriesServ.getAssetCatgerogyById(1);
		assertNotNull(assetCategory);
		assertEquals("AssetCatName", assetCategorydata.getName());
		assertEquals("AssetCatDesc", assetCategorydata.getDescription());

	}

	@Test
	public void updateAssetCategoryByIdSucessId() throws ApplicationException {
		AssetCategory assetCategory = new AssetCategory();
		assetCategory.setId(1);
		assetCategory.setName("AssetCatName");
		assetCategory.setDescription("AssetCatDesc");
		AssetCategoryRequest assetCategoryRequest = new AssetCategoryRequest();
		assetCategoryRequest.setId(1);
		assetCategoryRequest.setName("AssetCatName");
		assetCategoryRequest.setDescription("AssetCatDesc");
		when(assetCatgRepo.findById(1)).thenReturn(Optional.of(assetCategory));
		when(convertor.convertToAssetCategory(assetCategoryRequest)).thenReturn(assetCategory);
		AssetCategoryResponse assetCategorydata = assetCategoriesServ.updateAssetCategory(1, assetCategoryRequest);

		assertNotNull(assetCategory);
		assertEquals("AssetCatName", assetCategorydata.getName());
		assertEquals("AssetCatDesc", assetCategorydata.getDescription());
	
	}
	@Test
	public void deleteAssetCategoryByIdSucessId() {
		AssetCategory assetCategory = new AssetCategory();
		assetCategory.setId(1);
		assetCategory.setName("AssetCatName");
		assetCategory.setDescription("AssetCatDesc");
		when(assetCatgRepo.findById(1)).thenReturn(Optional.of(assetCategory));
	
		boolean assetCatgDeleteData = assetCategoriesServ.deleteAssetcategory(1);
		assertNotNull(assetCategory);
		assertTrue(assetCatgDeleteData);

	}
}
