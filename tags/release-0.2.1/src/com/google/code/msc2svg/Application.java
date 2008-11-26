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
import java.awt.Font;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.google.code.msc2svg.paint.PaintManager;



public class Application {

    public static void main(String[] args) throws IOException {
    	/* load properties */
    	Properties	props = new Properties();
    	props.load(new FileInputStream("application.props"));
    	
    	
    	/* create and initialize the application context */
    	Context context = new Context();
    	context.setFont(Font.decode(props.getProperty("font")));
    	context.setDescriptionFileName(props.getProperty("file.in"));
    	context.setArrawFill(Boolean.parseBoolean(props.getProperty("arrow.fill")));
    	context.setProcessesArray(props.getProperty("chart.processes").split(","));
    	context.setAbsolutStartPoint(new Point(
    			             Integer.parseInt(props.getProperty("chart.start.point.x").trim()),
    			             Integer.parseInt(props.getProperty("chart.start.point.y").trim())))

    	;
    	
    	context.setProcessColor(new Color(Integer.parseInt(props.getProperty("chart.processes.color"),16)));
    	
        for(char c = 'a'; c <= 'o'; c++)
        {
            String colorName = (new StringBuilder("colors.")).append(c).toString();
            context.getColors().put(colorName, new Color(Integer.parseInt(props.getProperty(colorName), 16)));
        }

    	
    	/* initialize the PaintManager */
    	PaintManager parser = new PaintManager(context);
    	parser.parseDescriptionFile();
    	
    	/* do the work */
    	parser.draw();
    	
    	/* export to svg file */
        context.getSvgGenerator().stream(props.getProperty("file.out"), false);
    }
}