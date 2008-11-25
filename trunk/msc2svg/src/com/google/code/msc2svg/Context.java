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

package com.google.code.msc2svg;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.util.HashMap;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;


public class Context {
	private Font font;
	
	private int padding;
	
	
	private Dimension msgGraphicDimension;
	
	private SVGGraphics2D svgGenerator;
    
    private String descriptionFileName;

    private boolean arrawFill = false;
        
    private Point absolutStartPoint; 
    
    
    private String processesArray[]; 
    
    private Color processColor;
    
    private HashMap<String, Color> colors;

    
    
    public Context()
    {
    	this.colors = new HashMap<String, Color>();
    	this.msgGraphicDimension = new Dimension();
    	this.absolutStartPoint = new Point();
    	this.absolutStartPoint.x = 0;
    	this.absolutStartPoint.y = 0;
    }
    
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public int getPadding() {
		return padding;
	}
	public void setPadding(int padding) {
		this.padding = padding;
	}
	public SVGGraphics2D getSvgGenerator() {
		
		if(this.svgGenerator == null)
		{

	        DOMImplementation domImpl =
	            GenericDOMImplementation.getDOMImplementation();

	        String svgNS = "http://www.w3.org/2000/svg";
	        Document document = domImpl.createDocument(svgNS, "svg", null);

	        // Create an instance of the SVG Generator.
	        this.svgGenerator = new SVGGraphics2D(document);

	        svgGenerator.setFont(this.font);

		}
		return this.svgGenerator;
	}
	public String getDescriptionFileName() {
		return descriptionFileName;
	}
	public void setDescriptionFileName(String descriptionFileName) {
		this.descriptionFileName = descriptionFileName;
	}
	public Dimension getMsgGraphicDimension() {
		return msgGraphicDimension;
	}
	public void setMsgGraphicDimension(Dimension msgGraphicDimension) {
		this.msgGraphicDimension = msgGraphicDimension;
	}

	public boolean isArrawFill() {
		return arrawFill;
	}

	public void setArrawFill(boolean arrawFill) {
		this.arrawFill = arrawFill;
	}

	public Point getAbsolutStartPoint() {
		return absolutStartPoint;
	}

	public void setAbsolutStartPoint(Point absolutStartPoint) {
		this.absolutStartPoint = absolutStartPoint;
	}

	public String[] getProcessesArray() {
		return processesArray;
	}

	public void setProcessesArray(String[] processesArray) {
		this.processesArray = processesArray;
	}

	public Color getProcessColor() {
		return processColor;
	}

	public void setProcessColor(Color processColor) {
		this.processColor = processColor;
	}

    public HashMap<String,Color> getColors()
    {
        return colors;
    }

    public void setColors(HashMap<String,Color> colors)
    {
        this.colors = colors;
    }	
	
	
}
