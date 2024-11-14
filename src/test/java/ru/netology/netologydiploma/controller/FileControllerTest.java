package ru.netology.netologydiploma.controller;

import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import ru.netology.netologydiploma.model.FileDetails;
import ru.netology.netologydiploma.model.PutRequest;
import ru.netology.netologydiploma.service.FileService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileControllerTest {
    @Mock
    FileService fileService;
    @InjectMocks
    FileController fileController;

    @Test
    void testGetFileList() {

        ArrayList<FileDetails> fileDetailsList = new ArrayList<>();
        when(fileService.getFileList(Mockito.any(), anyInt())).thenReturn(fileDetailsList);

        List<FileDetails> actualFileList = fileController.getFileList(new UserPrincipal("principal"), 1);
        assertSame(fileDetailsList, actualFileList);
        assertTrue(actualFileList.isEmpty());
        verify(fileService).getFileList(Mockito.any(), anyInt());
    }

    @Test
    void testSaveFile() throws IOException {
        UserPrincipal principal = new UserPrincipal("Ivan Ivanov");
        fileController.saveFile(principal, "file.txt",
                new MockMultipartFile("Name", new ByteArrayInputStream("Word".getBytes())));
        verify(fileService).saveFile(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void testGetFile() {
        fileController.getFile(new UserPrincipal("Ivan Ivanov"), null);
    }

    @Test
    void testRenameFile() {
        doNothing().when(fileService).renameFile(Mockito.any(), Mockito.any(), Mockito.any());
        UserPrincipal principal = new UserPrincipal("principal");
        fileController.renameFile(principal, "file.txt", new PutRequest("file.txt"));
        verify(fileService).renameFile(Mockito.any(), Mockito.any(), Mockito.any());
    }
}