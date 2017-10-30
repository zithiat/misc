package com.mycom.parser.http;

import com.mycom.parser.http.json.TargetObjectBuilder;
import com.mycom.parser.http.pojo.InBufffer;
import com.mycom.parser.http.pojo.Target;
import com.mycom.parser.http.utilities.json.GJsonParser;
import com.mycom.parser.http.utilities.json.ReaderUtil;
import com.mycom.parser.http.utilities.json.buffer.CharBuffer;
import com.mycom.parser.http.utilities.json.buffer.IndexBuffer;
import com.mycom.parser.processor.core.Retry;
import com.mycom.parser.processor.exception.GCEBadJsonException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * This class handles JSON parsing
 */
public class JsonParser
{


  private List<Target> targets;


  private final Logger logger = Logger.getLogger (JsonParser.class);



  public JsonParser ()
  {
  }



  public void readStreamWithGJsonParser (final InBufffer in) throws GCEBadJsonException
  {
    final int bufferLength = in.getInBuffer ().length ();

    CharBuffer charBuffer = null;
    IndexBuffer jsonElements = null;
    List<Target> targetList = new ArrayList<> ();

    try
    {
      charBuffer = ReaderUtil.readStream (new StringReader (in.getInBuffer ().toString ()), bufferLength);
      jsonElements = new IndexBuffer (bufferLength, true); // Default: 8192 or
                                                           // 65536
      GJsonParser parser = new GJsonParser ();
      parser.parse (charBuffer, jsonElements);
      targetList = TargetObjectBuilder.parseTargetJsonObject (charBuffer, jsonElements);
      if (parser != null)
      {
        parser = null;
      }
    }
    catch (IOException | GCEBadJsonException | ArrayIndexOutOfBoundsException e)
    {
      this.logger.error ("JSON parser error: " + e.getMessage (),e);
//      throw new GCEBadJsonException ();
    }

    this.targets = targetList;

    if (targetList != null)
    {
      targetList = null;
    }
    if (jsonElements != null)
    {
      jsonElements.clean ();
    }
    if (charBuffer != null)
    {
      charBuffer.clean ();
    }
    in.setInBuffer (null);
  }



  /**
   * Returns the List of Retry targets in JSON format
   */
  public String getRetryTargetsInJson (final List<Retry> retry)
  {
    final Gson gson = new Gson ();
    return gson.toJson (retry);
  }



  /**
   * @return the targets
   */
  public List<Target> getTargets ()
  {
    return this.targets;
  }



  /**
   * @param targets
   *          the targets to set
   */
  public void setTargets (final List<Target> targets)
  {
    this.targets = targets;
  }



  public void readUpdateInputStream (final InBufffer in) throws GCEBadJsonException
  {
    StringBuilder sb;
    try
    {
      final Object value = in.getInBuffer ();
      sb = (StringBuilder)value;

      final Gson gson = new Gson ();
      final JsonReader reader = new JsonReader (new StringReader (sb.toString ()));
      reader.setLenient (true); // Important for the JSON stream

      final Target[] gsonTargets = gson.fromJson (reader, Target[].class);

      this.targets = Arrays.asList (gsonTargets);
      // To be considered for Performance.
      // In case of the large String Object from incoming buffer --> clear it
      // Method m = in.getClass ().getDeclaredMethod ("setInBuffer",
      // StringBuilder.class);
      // Object empObj = m.invoke (in, "");
    }
    catch (final Exception e)
    {
      throw new GCEBadJsonException ("Invalid JSON Format of Request" + e);
    }
  }

}
