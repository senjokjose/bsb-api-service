package com.bsbApiService.fileProcessing.service;

import com.bsbApiService.bsb.repository.BSBDataRepository;
import com.bsbApiService.cache.CacheService;
import com.bsbApiService.fileProcessing.model.BSBFileTracking;
import com.bsbApiService.fileProcessing.model.BsbFileType;
import com.bsbApiService.fileProcessing.repository.BSBFileTrackingRepository;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;


public abstract class AbstractBSBFileProcessingService implements  BSBFileProcessingService{
    Logger logger = LogManager.getLogger(AbstractBSBFileProcessingService.class);
    @Autowired
    private BSBFileTrackingRepository bsbFileTrackingRepository;
    @Autowired
    protected BSBDataRepository bsbDataRepository;

    @Autowired
    private CacheService cacheService;

    @Override
    /**
     * This method will look at the ftp downloaded directory for any new files(BSB file full list, or BSB update file)
     * Make sure that the file is not processed yet(by comparing the tracking record). If the file is already processed, delete the file and exit.
     * Process the files, create a tracking record. Move the files to different location for future tracking
     * For update file, any changes in the record, an event will be generated to notify the users
     */
    public void processFile(String fileName) {
        if(checkFileAlreadyProcessed(fileName)){ //If file already processed, please log and skip
            logger.info(String.format("File %s is already processed.", fileName));
            return;
        }
        Long bsbFileTrackingId = createTrackingRecord(fileName).getId();
        try {
            processBSBFile(fileName, bsbFileTrackingId);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        cleanProcessedFile();
        notifyEvent();

    }

    /**
     * Check the tracking record table for an entry
     * @param fileName
     * @return
     */
    private boolean checkFileAlreadyProcessed(String fileName){
        return Optional.ofNullable(bsbFileTrackingRepository.findBSBFileTrackingByFileName(fileName)).isPresent();
    }

    /**
     * Move the processed file to back up directory, Delete the file from processing path
     */
    private void cleanProcessedFile(){
        //TODO
    }

    /**
     * Create an event in rabbit MQ to notify the cache update and  send notification events to customers through respective channels.
     */
    private void notifyEvent(){
       //Time being events are handled in the same method instead of creating a queue and listner(asynchronously)
        cacheService.evictAllCaches();
    }

    private BSBFileTracking createTrackingRecord(String fileName){
        BSBFileTracking bsbFileTracking = new BSBFileTracking(getBSBFileType(), fileName, new Date(), "System");//might be log server name too
        return bsbFileTrackingRepository.save(bsbFileTracking);
    }

    private Long getBsbFileTrackingId(String fileName){
        return bsbFileTrackingRepository.findBSBFileTrackingByFileName(fileName).getId();
    }

    abstract void processBSBFile(String fileName, Long fileTrackingId) throws IOException;

    abstract BsbFileType getBSBFileType();

}
