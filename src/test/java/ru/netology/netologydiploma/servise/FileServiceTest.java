package ru.netology.netologydiploma.servise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.netologydiploma.exceprion.InputDataException;
import ru.netology.netologydiploma.model.FileDetails;
import ru.netology.netologydiploma.reposipory.FileRepository;
import ru.netology.netologydiploma.service.FileService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {FileService.class})
@ExtendWith(SpringExtension.class)
class FileServiceTest {
    @MockBean
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @Test
    void testGetFileList() {
        ArrayList<FileDetails> fileDetailsList = new ArrayList<>();
        when(fileRepository.getFileList(Mockito.<String>any(), anyInt())).thenReturn(fileDetailsList);
        List<FileDetails> actualFileList = fileService.getFileList("UserLogin", 1);
        assertSame(fileDetailsList, actualFileList);
        assertTrue(actualFileList.isEmpty());
        verify(fileRepository).getFileList(Mockito.<String>any(), anyInt());
    }

    @Test
    void testGetFileListInputDataException() {
        when(fileRepository.getFileList(Mockito.<String>any(), anyInt())).thenThrow(new InputDataException(" "));
        assertThrows(InputDataException.class, () -> fileService.getFileList("UserLogin", 1));
        verify(fileRepository).getFileList(Mockito.<String>any(), anyInt());
    }

    @Test
    void testSaveFile() throws IOException {
        doNothing().when(fileRepository).saveFile(Mockito.<String>any(), Mockito.<String>any(), Mockito.<MultipartFile>any());
        fileService.saveFile("Login", "file.txt", new MockMultipartFile("Name", new ByteArrayInputStream("Word".getBytes())));
        verify(fileRepository).saveFile(Mockito.<String>any(), Mockito.<String>any(), Mockito.<MultipartFile>any());
    }

    @Test
    void testSaveFileInputDataException() {
        assertThrows(InputDataException.class, () -> fileService.saveFile("Login", null, new MockMultipartFile("Name", new ByteArrayInputStream("Word".getBytes()))));
    }

    @Test
    void testDeleteFile() {
        doNothing().when(fileRepository).deleteFile(Mockito.<String>any(), Mockito.<String>any());
        fileService.deleteFile("Login", "file.txt");
        verify(fileRepository).deleteFile(Mockito.<String>any(), Mockito.<String>any());
    }

    @Test
    void testDeleteFileInvalidFileName() {
        assertThrows(InputDataException.class, () -> fileService.deleteFile("Login", null));
    }

    @Test
    void testDeleteFileInvalidFile() {
        doThrow(new InputDataException("Msg")).when(fileRepository).deleteFile(Mockito.<String>any(), Mockito.<String>any());
        assertThrows(InputDataException.class, () -> fileService.deleteFile("Login", "file.txt"));
        verify(fileRepository).deleteFile(Mockito.<String>any(), Mockito.<String>any());
    }

    @Test
    void testGetFile() {
        when(fileRepository.getFile(Mockito.<String>any(), Mockito.<String>any())).thenReturn("Word".getBytes());
        byte[] actualFile = fileService.getFile("Login", "file.txt");
        assertEquals(8, actualFile.length);
        verify(fileRepository).getFile(Mockito.<String>any(), Mockito.<String>any());
    }

    @Test
    void testGetFileInvalidFileName() {
        assertThrows(InputDataException.class, () -> fileService.getFile("Login", null));
    }

    @Test
    void testGetFileInvalidFile() {
        when(fileRepository.getFile(Mockito.<String>any(), Mockito.<String>any())).thenThrow(new InputDataException(" "));
        assertThrows(InputDataException.class, () -> fileService.getFile("Login", "file.txt"));
        verify(fileRepository).getFile(Mockito.<String>any(), Mockito.<String>any());
    }

    @Test
    void testRenameFile() {
        doNothing().when(fileRepository).renameFile(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        fileService.renameFile("Login", "file.txt", "file.txt");
        verify(fileRepository).renameFile(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    }

    @Test
    void testRenameFileInvalidFileName() {
        assertThrows(InputDataException.class, () -> fileService.renameFile("Login", null, "file.txt"));
    }

    @Test
    void testRenameFileInvalidNewFileName() {
        assertThrows(InputDataException.class, () -> fileService.renameFile("Login", "file.txt", null));
    }

}