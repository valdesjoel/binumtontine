package com.example.binumtontine.activity.pdf;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

//import org.alfresco.error.AlfrescoRuntimeException;
//import org.alfresco.model.ContentModel;
//import org.alfresco.service.cmr.repository.ContentReader;
//import org.alfresco.service.cmr.repository.ContentWriter;
//import org.alfresco.service.cmr.repository.NodeRef;
//import org.apache.poi.xwpf.usermodel.PositionInParagraph;
import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.xwpf.usermodel.TextSegement;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;
//import org.apache.poi.xwpf.usermodel.XWPFTable;
//import org.apache.poi.xwpf.usermodel.XWPFTableCell;
//import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.PositionInParagraph;
import org.apache.poi.xwpf.usermodel.TextSegment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.json.JSONObject;
//import org.slf4j.LoggerFactory;

public class WordReplacer
{
//    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WordReplacer.class);
    private static final Logger LOGGER = LoggerFactory.getLogger(WordReplacer.class);

//    private EcmServiceRegistry            serviceRegistry;

//    public WordReplacer(EcmServiceRegistry serviceRegistry)
//    {
//        super();
//        this.serviceRegistry = serviceRegistry;
//    }

//    public NodeRef processWordReplace(NodeRef templateNodeRef, NodeRef outputParentNode, JSONObject keysValues) throws IOException
    public void processWordReplace(String templateNodeRef, String outputParentNode, JSONObject keysValues) throws IOException
    {

        try
        {

            Path  path = null; ;
            XWPFDocument     doc     = new XWPFDocument(OPCPackage.open("input.docx"));

            Iterator<String> keysItr = keysValues.keys();
            while (keysItr.hasNext())
            {
                String key = keysItr.next();
                // on remplace dans les paragraph simple
                replaceInParagraphs(doc.getParagraphs(), key, keysValues.getString(key));

                for (XWPFTable tbl : doc.getTables())
                {
                    for (XWPFTableRow row : tbl.getRows())
                    {
                        for (XWPFTableCell cell : row.getTableCells())
                        {
                            // on remplace dans les paragraph inclus dans des cellules
                            replaceInParagraphs(cell.getParagraphs(), key, keysValues.getString(key));
                        }
                    }
                }
            }
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("myFile.txt"), StandardCharsets.UTF_8));
            writer.write("text to write");
        }
        catch (Exception e)
        {
            LOGGER.error("Exception:", e);
        }
    }

    private void replaceInParagraphs(List<XWPFParagraph> xwpfParagraphs, String key, String value)
    {
        for (XWPFParagraph p : xwpfParagraphs)
        {
            List<XWPFRun> runs  = p.getRuns();
            // on cherche le text dans le paragraph
            TextSegment  found = p.searchText(key, new PositionInParagraph());

            if (found != null)
            {
                // on parcourt les runs qui contiennent la chaine trouvé
                int beginRun = found.getBeginRun();
                for (int runPos = beginRun; runPos <= found.getEndRun(); runPos++ )
                {
                    if (runPos != beginRun)
                    {
                        // on met le texte du run courant dans le 1er run
                        runs.get(beginRun).setText(runs.get(beginRun).getText(beginRun) + runs.get(runPos).getText(0), 0);
                        // on vide le texte du run courant
                        runs.get(runPos).setText("", 0);
                    }
                }
                runs = p.getRuns();
                runs.get(beginRun).setText(runs.get(beginRun).getText(0).replace(key, value), 0);
//                LOGGER.info("Runs content - {}", runs.get(beginRun).getText(0));
            }
        }
    }
}

