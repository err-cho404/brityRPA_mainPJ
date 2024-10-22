package co.kr.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.kr.shop.mapper.AdminMapper;
import co.kr.shop.mapper.AttachMapper;
import co.kr.shop.mapper.ProductMapper;
import co.kr.shop.model.AttachImageVO;
import co.kr.shop.model.Criteria;
import co.kr.shop.model.ProductVO;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private AttachMapper attachMapper;
	@Autowired
	private AdminMapper adminMapper;
	
	/* 상품 검색 */
	@Override
	public List<ProductVO> getProductList(Criteria cri) {
		
		log.info("getProductList().......");
		
		String type = cri.getType();
		String[] typeArr = type.split("");
		//String[] authorArr = productMapper.getAuthorIdList(cri.getKeyword());
		
		
		/*if(type.equals("A") || type.equals("AC") || type.equals("AT") || type.equals("ACT")) {
			if(authorArr.length == 0) {
				return new ArrayList();
			}
		}
		
		for(String t : typeArr) {
			if(t.equals("A")) {
				cri.setAuthorArr(authorArr);
			}
		}		*/
		
		List<ProductVO> list = productMapper.getProductList(cri);
		
		list.forEach(product -> {
			
			int productId = product.getProductId();
			
			List<AttachImageVO> imageList = attachMapper.getAttachList(productId);
			
			product.setImageList(imageList);
			
		});
		
		return list;
	}

	/* 상품 총 갯수 */
	@Override
	public int productGetTotal(Criteria cri) {
		
		log.info("productGetTotal().......");
		
		return productMapper.productGetTotal(cri);
		
	}
	@Override
	public ProductVO getProductInfo(int productId) {
		
		ProductVO productInfo = productMapper.getProductInfo(productId);
		productInfo.setImageList(adminMapper.getAttachInfo(productId));
		
		return productInfo;
	}
	/* 상품별 총 판매 갯수*/
	@Override
	public int salesOrderTotal(Criteria cri) {
		return adminMapper.salesOrderTotal(cri);
	}
	@Override
    public List<ProductVO> getProductStock(){
        log.info("getAllProductStock()......");
        List<ProductVO> productList = productMapper.getProductStock();

        productList.forEach(product -> {
            int productId = product.getProductId();
            List<AttachImageVO> imageList = attachMapper.getAttachList(productId);
            product.setImageList(imageList);
        });

        return productList;
    }
	/* 색상 목록 */
    @Override
    public List<ProductVO> productGetList(Criteria cri) throws Exception {
        
        return productMapper.productGetList(cri);
    }
    @Override
    public List<ProductVO> getTopSoldProducts() {
    	List<ProductVO> list = productMapper.getTopSoldProducts();
		
		list.forEach(dto -> {
			
			int productId = dto.getProductId();
			
			List<AttachImageVO> imageList = attachMapper.getAttachList(productId);
			
			dto.setImageList(imageList);
			
		});				
		
		
		return list;
    }
}
