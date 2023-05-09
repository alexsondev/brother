package com.brother.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.brother.vo.*;
import com.brother.component.*;
import java.text.Normalizer;

@Path("/file")
public class UploadComponentRest {

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(MultipartFormDataInput input) throws IOException {

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

        // Get file data to save
        List<InputPart> inputParts = uploadForm.get("file");
        String parentDocumentId = uploadForm.get("parentDocumentId").get(0).getBodyAsString();

        for (InputPart inputPart : inputParts) {
            try {

                UserVO currentUser = UserComponent.getCurrentUser();
                TenantVO tenant = TenantComponent.getTenantData();

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                String fileName = getFileName(header);

                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(inputStream);

                // ALTERAR PARA PEGAR DOS PARAMETROS
                String path = tenant.getData("dirUpload") + "/" + currentUser.getCode();

                File customDir = new File(path);

                if (!customDir.exists()) {
                    customDir.mkdir();
                }

                System.out.println(String.format("---- file upload fileName: %s", fileName));

                writeFile(bytes, customDir.getCanonicalPath() + File.separator + fileName);

                DocumentVO documentVO =
                        DocumentComponent.createDocument(fileName, parentDocumentId, true, true);

                return Response.status(Response.Status.OK).entity(documentVO).build();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getFileName(MultivaluedMap<String, String> header) {

        System.out.println(String.format("---- getFileName"));

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {

            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");

                finalFileName = Normalizer.normalize(finalFileName, Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "");

                return finalFileName;
            }
        }
        return "unknown";
    }

    // Utility method
    private void writeFile(byte[] content, String filename) throws IOException {

        System.out.println(String.format("---- file writeFile filename: %s", filename));

        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();
    }
}
