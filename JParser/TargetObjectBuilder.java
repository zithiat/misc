package com.mycom.parser.http.json;

import com.mycom.parser.http.pojo.Chparam;
import com.mycom.parser.http.pojo.ConInfo;
import com.mycom.parser.http.pojo.MnoCh;
import com.mycom.parser.http.pojo.Target;
import com.mycom.parser.http.utilities.json.buffer.CharBuffer;
import com.mycom.parser.http.utilities.json.buffer.IndexBuffer;
import com.mycom.parser.http.utilities.json.core.ElementTypes;
import com.mycom.parser.http.utilities.json.core.JsonNavigator;
import com.mycom.parser.processor.exception.GCEBadJsonException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import org.apache.log4j.Logger;


public final class TargetObjectBuilder
{
  
//  private static final Logger logger = Logger.getLogger (TargetObjectBuilder.class);

  private TargetObjectBuilder ()
  {
  }



  /**
   *
   * This is to parse a tokenized and indexed JSON message into List of Target
   *
   * @param charBuffer
   * @param jsonElements
   * @return
   * @throws GCEBadJsonException
   */
  public static List<Target> parseTargetJsonObject (final CharBuffer charBuffer, final IndexBuffer jsonElements) throws GCEBadJsonException
  {
    final com.mycom.parser.http.utilities.json.core.JsonNavigator navigator = new JsonNavigator (charBuffer, jsonElements);
    final List<Target> parsedTargets = TargetObjectBuilder.parseJsonObject (navigator);
    charBuffer.clean ();
    jsonElements.clean ();
    navigator.clean ();
    return parsedTargets;
  }



  /**
   * Parsing using integer matching. Each json key will be converted to integer
   * value (as a combination of charAt()), then value will be collected to
   * Target POJO
   *
   * @param navigator
   * @return
   * @throws GCEBadJsonException
   */
  @SuppressWarnings("unchecked")
  private static List<Target> parseJsonObject (final com.mycom.parser.http.utilities.json.core.JsonNavigator navigator) throws GCEBadJsonException
  {
//    long start, end;
//    start = System.nanoTime ();
    final List<Target> listTar = new ArrayList<Target> ();

    Target tar = new Target ();
    Chparam chparam = null;
    ConInfo conInfo = null;
    MnoCh mnoCh = null;

    final HashMap<String, String> persoData = new HashMap<String, String> ();

    navigator.next ();

    int beginPoint = 0;
    int endPoint = 0;

    String fieldName = "";
//    int fieldValue = 0;
    String valueStr = "";
    boolean persoChecker = false;
    boolean chparamChecker = false;
    boolean conInfoChecker = false;

    try
    {
      while (navigator.type () != ElementTypes.JSON_ARRAY_END)
      {
        if (navigator.type () == ElementTypes.JSON_OBJECT_START)
        {
          beginPoint++;
        }

        if (navigator.type () == ElementTypes.JSON_OBJECT_END)
        {
          endPoint++;
        }

        // the , between tags
        if (navigator.type () == ElementTypes.JSON_ARRAY_VALUE_NULL)
        {
          throw new GCEBadJsonException ("JSON format error: JSON_ARRAY_VALUE_NULL");
        }

        // navigator.next (); // Moving into field value

        if (navigator.type () != 2 && navigator.type () != 9 && navigator.type () != 1)
        {
          if (navigator.asString () == null)
          {
            throw new GCEBadJsonException ("JSON format error: field value null " + navigator.type ());
          }
          else
          {
            fieldName = navigator.asString ();

            if (!fieldName.isEmpty ())
            {
//              for (int i = 0; i < fieldName.length (); ++i)
//              {
//                fieldValue += fieldName.charAt (i);
//              }
              // Moving into field value
              navigator.next ();

              // Check if it's nested JSON object
              if (navigator.type () == ElementTypes.JSON_OBJECT_START)
              {
                beginPoint++;
              }

              /**
               * navigator.type()<br>
               * 1: object start<br>
               * 11: string data type<br>
               * 12: string with encoded characters, like \t or \u2345<br>
               * 13: number <br>
               * 14: boolean <br>
               * 15: null
               */
              // if (navigator.type () != 11 && navigator.type () != 1)
              // {
              // throw new GCEBadJsonException
              // ("JSON format error: wrong format");
              // }

              if (navigator.type () == 11) // String
              {
                valueStr = navigator.asString ();
              }

              // Adding to Target POJO
              switch (fieldName)
              {
              case "cor": // cor = 324
                if (valueStr.isEmpty ())
                {
                  if (navigator.length () > 0)
                  {
                    tar.setCor (navigator.asLong ());
                  }
                }
                else
                {
                  throw new GCEBadJsonException ("JSON parsing error: scnId should be Long " + valueStr);
                }
                break;
              case "con": // con = 320
                tar.setCon (valueStr);
                break;
              case "cmpRef": // cmpRef = 605
                // Campaign Reference is used only if wibditr is null
                if (valueStr.isEmpty ())
                {
                  if (navigator.length () > 0)
                  {
                    tar.setCmpRef (navigator.asInt ());
                  }
                }
                else
                {
                  throw new GCEBadJsonException ("JSON parsing error: scnId should be Integer " + valueStr);
                }
                break;
              case "mnoCh": //  = 501
                mnoCh = new MnoCh ();
                break;
              case "mccmnc": // mccmnc = 625
                if (mnoCh != null)
                {
                  mnoCh.setMccmnc (valueStr);
                }
                break;
              case "ch": // ch = 203
                if (mnoCh != null)
                {
                  mnoCh.setCh (valueStr);
                }
                break;
              case "scnId": // scnId = 497
                if (valueStr.isEmpty ())
                {
                  if (navigator.length () > 0)
                  {
                    tar.setScnId (navigator.asInt ());
                  }
                }
                else
                {
                  throw new GCEBadJsonException ("JSON parsing error: scnId should be Integer " + valueStr);
                }
                break;
              case "scnVal": // scnVal = 615
//                logger.debug (navigator.asString());
                tar.setScnVal (navigator.asString());
                break;
              case "chparam": // chParam = 732
                chparam = new Chparam ();
                chparamChecker = true;
                break;
              case "chid": // chid = 408
                if (chparam != null)
                {
                  chparam.setChid (valueStr);
                }
                break;
              case "smsvalper": // smsvalper = 989
                if (chparam != null)
                {
                  chparam.setSmsvalper (valueStr);
                }
                break;
              case "invvalper": // invvalper = 983
                if (chparam != null)
                {
                  chparam.setInvvalper (valueStr);
                }
                break;
              case "invprio": // invprio = 775
                if (chparam != null)
                {
                  chparam.setInvprio (valueStr);
                }
                break;
              case "wibditr": // wibditr = 757
                if (chparam != null)
                {
                  if (valueStr.isEmpty ())
                  {
                    chparam.setWibDitr (navigator.asBoolean ());
                  }
                  else
                  {
                    throw new GCEBadJsonException ("JSON parsing error: wibditr should not be String " + valueStr);
                  }
                }
                break;
              case "laGroup": // laGroup
                if (chparam != null)
                {
                  chparam.setLaGroup (valueStr.isEmpty () ? "" : valueStr.trim ());
                }
                break;
              case "perso": // perso = 553
                persoChecker = true;
                break;
              case "conInfo": // conInfo = 716
                conInfo = new ConInfo ();
                conInfoChecker = true;
                break;
              case "browser": // browser = 772
                if (conInfo != null)
                {
                  if (!valueStr.isEmpty ())
                  {
                    conInfo.setBrowser (valueStr);
                  }
                  else
                  {
                    throw new GCEBadJsonException ("JSON parsing error: browser should be String " + valueStr);
                  }
                }
                break;
              case "iccid": // iccId=476, iccid=508
                if (conInfo != null)
                {
                  conInfo.setIccId (valueStr);
                }
                break;
              case "notifEndDt": // notifEndDt = 1007
                // Notification end date has been removed
                // tar.setNotifEndDt (navigator.asString ());
                break;
              default:
                break;
              }
              // persoChecker == true && chparamChecker == false &&
              // conInfoChecker == false
              if (persoChecker && !chparamChecker && !conInfoChecker)
              {
                persoData.put (fieldName, valueStr);
              }
            }
            else
            {
              throw new GCEBadJsonException ("JSON format error: fieldName empty " + navigator.type ());
            }
          }
        }
        else
        {
          if (navigator.type () == ElementTypes.JSON_OBJECT_END)
          {
            persoChecker = false;
            chparamChecker = false;
            conInfoChecker = false;
          }
        }
//        fieldValue = 0;
        fieldName = "";
        valueStr = "";

        if (beginPoint == endPoint)
        {
          if (chparam != null)
          {
            tar.setChparam (chparam);
          }
          if (conInfo != null)
          {
            tar.setConInfo (conInfo);
          }
          tar.setPerso ((HashMap<String, String>)(persoData.clone ()));
          if (mnoCh != null)
          {
            tar.setMnoCh (mnoCh);
          }
          listTar.add (tar);
          // Reset for new incoming JSON
          beginPoint = 0;
          endPoint = 0;
          tar = new Target ();
        }

        // Moving into field value
        navigator.next ();

      } // end while
      if (navigator.type () == 9)
      {
        if (beginPoint != endPoint)
        {
          throw new GCEBadJsonException ("JSON format error: wrong format: not match opening - closing format");
        }
      }
    }
    catch (final Exception e)
    {
      throw new GCEBadJsonException ("JSON parsing error: " + e);
    }
//    end = System.nanoTime ();
//    logger.debug ("Parsing time:" + (end - start));
    return listTar;
  }
}
