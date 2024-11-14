package ru.netology.netologydiploma.service;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.netologydiploma.model.FileDetails;
import ru.netology.netologydiploma.exceprion.InputDataException;
import ru.netology.netologydiploma.reposipory.FileRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private static final Logger log = Logger.getLogger(FileService.class);

    private void checkFileName(String fileName) {
        if (fileName == null) {
            log.error("Error input data");
            throw new InputDataException("Error input data");
        }
    }

    public List<FileDetails> getFileList(String userLogin, int limit) {
        log.info("getFileList by " + userLogin);
        return fileRepository.getFileList(userLogin, limit);
    }

    public void saveFile(String login, String fileName, MultipartFile file) {
        checkFileName(fileName);
        fileRepository.saveFile(login, fileName, file);
    }

    public void deleteFile(String login, String fileName) {
        checkFileName(fileName);
        fileRepository.deleteFile(login, fileName);

    }

    public byte[] getFile(String login, String fileName) {
        checkFileName(fileName);
        return fileRepository.getFile(login, fileName);
    }

    public void renameFile(String login, String fileName, String newFileName) {
        checkFileName(fileName);
        checkFileName(newFileName);
        fileRepository.renameFile(login, fileName, newFileName);
    }
}