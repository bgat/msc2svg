/**
 * Author: Aziz Hammadi
 *
 * This file is part of msc2svg.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */


package com.google.code.msc2svg.model;

import java.awt.Color;

public class Message
{
	public static enum MODE
	{
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT,
        LEFT_AND_RIGHT
	};
	
    private String msg;
    private Color color;
    private MODE mode;
  
	

    public Message()
    {
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public String toString()
    {
        String s = "{";
        s = (new StringBuilder(String.valueOf(s))).append("Mode = ").append(mode.toString()).toString();
        s = (new StringBuilder(String.valueOf(s))).append(",").toString();
        s = (new StringBuilder(String.valueOf(s))).append("Msg = ").append(msg.toString()).toString();
        s = (new StringBuilder(String.valueOf(s))).append(",").toString();
        s = (new StringBuilder(String.valueOf(s))).append("Color = ").append(color.toString()).toString();
        s = (new StringBuilder(String.valueOf(s))).append("}").toString();
        return s;
    }

}
