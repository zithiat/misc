package com.mycom.parser.http.utilities.json;

import com.mycom.parser.http.utilities.json.buffer.CharBuffer;
import com.mycom.parser.http.utilities.json.buffer.IndexBuffer;
import com.mycom.parser.http.utilities.json.core.ElementTypes;
import com.mycom.parser.http.utilities.json.core.JsonParserException;

public class GJsonParser
{

  private static final byte FIELD_NAME = 1;

  private static final byte FIELD_VALUE = 2;

  private static final byte OBJECT = 3;

  private static final byte ARRAY = 4;

  private byte[] stateStack = new byte[1024]; // Default: 256

  private int stateIndex = 0;

  private int position = 0;

  private int elementIndex = 0;



  public void parse (CharBuffer buffer, IndexBuffer elementBuffer)
  {
    this.position = 0;
    this.elementIndex = 0;
    this.stateIndex = 0;
    this.stateStack[stateIndex] = FIELD_NAME;

    int prevIndex = 0;

    for (; position < buffer.length; position++)
    {

      switch (buffer.data[position])
      {
      case '{':
      {
        setElementDataLength1 (elementBuffer, elementIndex, ElementTypes.JSON_OBJECT_START, position);
        elementIndex++;
        pushState (OBJECT);
        setState (FIELD_NAME);
        break;
      }
      case '}':
      {
        setElementDataLength1 (elementBuffer, elementIndex, ElementTypes.JSON_OBJECT_END, position);
        elementIndex++;
        popState ();
        break;
      }
      case '[':
      {
        setElementDataLength1 (elementBuffer, elementIndex, ElementTypes.JSON_ARRAY_START, position);
        elementIndex++;
        pushState (ARRAY);
        setState (FIELD_VALUE);
        break;
      }
      case ']':
      {
        setElementDataLength1 (elementBuffer, elementIndex, ElementTypes.JSON_ARRAY_END, position);
        elementIndex++;
        popState ();
        break;
      }
      case 'f':
      {
        parseFalse (buffer, elementBuffer);
        elementIndex++;
        break;
      }
      case 't':
      {
        parseTrue (buffer, elementBuffer);
        elementIndex++;
        break;
      }
      case 'n':
      {
        parseNull (buffer, elementBuffer);
        elementIndex++;
        break;
      }

      case '0':
        ;
      case '1':
        ;
      case '2':
        ;
      case '3':
        ;
      case '4':
        ;
      case '5':
        ;
      case '6':
        ;
      case '7':
        ;
      case '8':
        ;
      case '9':
      {
        parseNumberToken (buffer, elementBuffer);
        elementIndex++;
      }
        break;

      case '"':
      {
        parseStringToken (buffer, elementBuffer, elementIndex, position);
        elementIndex++;
        break;
      }
      case ':':
      {
        if (isEmptyValue (buffer))
        {
          setElementDataNoLength (elementBuffer, elementIndex, ElementTypes.JSON_PROPERTY_VALUE_EMPTY, position);
          elementIndex++;
          setState (FIELD_NAME);
        }
        else
        {
          setState (FIELD_VALUE);
        }
        break;
      }
      case ',':
      {
        if (prevIndex == elementIndex)
        {
          throw new JsonParserException ("JSON format error: the \",\"");
        }
        setState (this.stateStack[this.stateIndex - 1] == ARRAY ? FIELD_VALUE : FIELD_NAME);
        prevIndex = elementIndex;
        break;
      }

      }

    }
    elementBuffer.count = this.elementIndex;

    this.position = 0;
    this.elementIndex = 0;
    this.stateIndex = 0;
    this.stateStack[stateIndex] = FIELD_NAME;
  }



  private final int parseStringToken (CharBuffer buffer, IndexBuffer elementBuffer, int elementIndex, int position)
  {
    int tempPos = position;
    boolean containsEncodedChars = false;
    boolean endOfStringFound = false;
    while (!endOfStringFound)
    {
      tempPos++;
      switch (buffer.data[tempPos])
      {
      case '"':
      {
        endOfStringFound = buffer.data[tempPos - 1] != '\\';
        break;
      }
      case '\\':
      {
        containsEncodedChars = true;
        break;
      }
      }
    }

    if (this.stateStack[this.stateIndex - 1] == OBJECT)
    {
      if (this.stateStack[this.stateIndex] == FIELD_NAME)
      {
        setElementData (elementBuffer, elementIndex, ElementTypes.JSON_PROPERTY_NAME, position + 1, tempPos - position - 1);
      }
      else
      {
        if (containsEncodedChars)
        {
          setElementData (elementBuffer, elementIndex, ElementTypes.JSON_PROPERTY_VALUE_STRING_ENC, position + 1, tempPos - position - 1);
        }
        else
        {
          setElementData (elementBuffer, elementIndex, ElementTypes.JSON_PROPERTY_VALUE_STRING, position + 1, tempPos - position - 1);
        }
      }
    }
    else
    { // this.stateStack[this.stateIndex -1] == ARRAY
      if (containsEncodedChars)
      {
        setElementData (elementBuffer, elementIndex, ElementTypes.JSON_ARRAY_VALUE_STRING_ENC, position + 1, tempPos - position - 1);
      }
      else
      {
        setElementData (elementBuffer, elementIndex, ElementTypes.JSON_ARRAY_VALUE_STRING, position + 1, tempPos - position - 1);
      }
    }

    this.position = tempPos;
    return tempPos;
  }



  private boolean parseTrue (CharBuffer buffer, IndexBuffer elementBuffer)
  {
    if (buffer.data[this.position + 1] == 'r' && buffer.data[this.position + 2] == 'u' && buffer.data[this.position + 3] == 'e')
    {
      if (this.stateStack[this.stateIndex - 1] == OBJECT)
      {
        setElementData (elementBuffer, this.elementIndex, ElementTypes.JSON_PROPERTY_VALUE_BOOLEAN, this.position, 4);
      }
      else
      {
        setElementData (elementBuffer, this.elementIndex, ElementTypes.JSON_ARRAY_VALUE_BOOLEAN, this.position, 4);
      }
      this.position += 3; // +4, but the outer for-loop will add 1 too.
      return true;
    }
    return false;
  }



  private boolean parseFalse (CharBuffer buffer, IndexBuffer elementBuffer)
  {
    if (buffer.data[this.position + 1] == 'a' && buffer.data[this.position + 2] == 'l' && buffer.data[this.position + 3] == 's' && buffer.data[this.position + 4] == 'e')
    {
      if (this.stateStack[this.stateIndex - 1] == OBJECT)
      {
        setElementData (elementBuffer, this.elementIndex, ElementTypes.JSON_PROPERTY_VALUE_BOOLEAN, this.position, 5);
      }
      else
      {
        setElementData (elementBuffer, this.elementIndex, ElementTypes.JSON_ARRAY_VALUE_BOOLEAN, this.position, 5);
      }
      this.position += 4; // +5, but the outer for-loop will add 1 too
      return true;
    }
    return false;
  }



  private void parseNumberToken (CharBuffer buffer, IndexBuffer elementBuffer)
  {
    int tempPos = this.position;
    boolean isEndOfNumberFound = false;
    while (!isEndOfNumberFound)
    {
      tempPos++;
      switch (buffer.data[tempPos])
      {
      case '0':
        ;
      case '1':
        ;
      case '2':
        ;
      case '3':
        ;
      case '4':
        ;
      case '5':
        ;
      case '6':
        ;
      case '7':
        ;
      case '8':
        ;
      case '9':
        ;
      case '.':
        break; // todo check for double . in numbers.

      default:
      {
        isEndOfNumberFound = true;
      }
      }
    }
    if (this.stateStack[this.stateIndex - 1] == OBJECT)
    {
      setElementData (elementBuffer, this.elementIndex, ElementTypes.JSON_PROPERTY_VALUE_NUMBER, this.position, tempPos - this.position);
    }
    else
    {
      setElementData (elementBuffer, this.elementIndex, ElementTypes.JSON_ARRAY_VALUE_NUMBER, this.position, tempPos - this.position);
    }
    this.position = tempPos - 1; // -1 because the outer for-loop adds 1 to the
                                 // position too
  }



  private boolean parseNull (CharBuffer buffer, IndexBuffer elementBuffer)
  {
    if (buffer.data[this.position + 1] == 'u' && buffer.data[this.position + 2] == 'l' && buffer.data[this.position + 3] == 'l')
    {
      if (this.stateStack[this.stateIndex - 1] == OBJECT)
      {
        setElementDataNoLength (elementBuffer, this.elementIndex, ElementTypes.JSON_PROPERTY_VALUE_NULL, this.position);
      }
      else
      {
        setElementDataNoLength (elementBuffer, this.elementIndex, ElementTypes.JSON_ARRAY_VALUE_NULL, this.position);
      }
      return true;
    }
    return false;

  }



  private void setState (byte state)
  {
    this.stateStack[this.stateIndex] = state;
  }



  private void pushState (byte state)
  {
    this.stateStack[this.stateIndex] = state;
    this.stateIndex++;
  }



  private void popState ()
  {
    this.stateIndex--;
  }



  private final void setElementDataNoLength (IndexBuffer elementBuffer, int index, byte type, int position)
  {
    elementBuffer.type[index] = type;
    elementBuffer.position[index] = position;
  }



  private final void setElementDataLength1 (IndexBuffer elementBuffer, int index, byte type, int position)
  {
    elementBuffer.type[index] = type;
    elementBuffer.position[index] = position;
    elementBuffer.length[index] = 1;
  }



  private final void setElementData (IndexBuffer elementBuffer, int index, byte type, int position, int length)
  {
    elementBuffer.type[index] = type;
    elementBuffer.position[index] = position;
    elementBuffer.length[index] = length;
  }

  private boolean isEmptyValue (CharBuffer buffer)
  {
    int valuePosition = position + 1;
    while (valuePosition < buffer.data.length)
    {
      if(buffer.data[valuePosition] == '\n'){
        valuePosition++;
        continue;
      }
      if (buffer.data[valuePosition] == ',')
      {
        return true;
      }
      break;
    }
    return false;
  }
}
