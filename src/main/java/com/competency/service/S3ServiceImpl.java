package com.competency.service;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;

@Service
public class S3ServiceImpl implements S3Service {

	final static Logger logger = LoggerFactory.getLogger(S3ServiceImpl.class);
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Value("${cloud.aws.bucket}")
	private String bucketName;
	
	@Value("${cloud.aws.bucket.directory}")
	private String directory;
	
	@Override
	public byte[] readFile(String key) {
		logger.debug("bucket={}, key={}", bucketName, directory + key+".JPG");
		byte[] bytes;
		S3Object s3Obj = amazonS3.getObject(bucketName, directory + key+".JPG");
		logger.info("key={}, contentType={}, length={}", key, 
			s3Obj.getObjectMetadata().getContentType(), s3Obj.getObjectMetadata().getContentLength());
		bytes = new byte[(int) s3Obj.getObjectMetadata().getContentLength()];
		BufferedInputStream bis = new BufferedInputStream(s3Obj.getObjectContent());
		try {
			bis.read(bytes);
			bis.close();
		} catch (IOException io) {
			logger.error("Error reading key={} from Amazon S3", key);
			io.printStackTrace(System.out);
		} finally {
			try {
				bis.close();
			} catch (IOException e) {}
		}
		return bytes;
	}

}