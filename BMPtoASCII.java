package lab06.asciiart;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A class that can read pixel data for Windows Bitmap (BMP) image files and
 * write out a text file containing an ASCII art rendering of the image.
 * 
 * @author Grant Braught
 * @version Oct 26, 2016
 * 
 * @author Khanh Le & David Grace
 * @version November 10th, 2019
 */
public class BMPtoASCII {

	static int[][] pixelList;
    private String filename;
    private static final int OFFSET = 1 + 1 + 4 + 4 + 4 + 4 + 4 + 4;
	static int width;
	static int height;
	
	/** 
	 * Create a new BMPtoASCII converter for the specified filename.
	 * 
	 * @param filename
	 *            the path to a file, but not containing the extension (i.e.
	 *            dir1/dir2/cat not dir1/dir2/cat.bmp).
	 */
	public BMPtoASCII(String filename) {
		 this.filename = filename;
		 width = 0;
		 height = 0;
	}

	
	/**
	 * Read the file specified in the constructor (adding a .bmp extension) into
	 * an array of intensity values (i.e. average the RGB values and store them
	 * into a 2d array of ints.)
	 */
	public void readFile() {
		DataInputStream dis = null; 
		try {
			dis = new DataInputStream(new FileInputStream(filename+".bmp")); 
			
			dis.skip(1 + 1 + 4 + 4);
			int offset = Integer.reverseBytes(dis.readInt());
			dis.skip(4);
			width = Integer.reverseBytes(dis.readInt());// 4 bytes, num of pixels
			height = Integer.reverseBytes(dis.readInt());// 4 bytes, num of pixels
			
			dis.skip(offset - OFFSET);
			pixelList = new int[height][width]; 
			
			
			int padding = (width*3) % 4;
			
			for (int row = 0; row< height; row++)/*go through all of the row*/ {
				for(int col = 0; col< width; col++)/*go through every column in each row*/ {
					int R = dis.readUnsignedByte();
					int G = dis.readUnsignedByte();
					int B = dis.readUnsignedByte(); 
					
					pixelList[row][col]= Math.round((R+G+B)/3);
					
				}
				dis.skip(padding);
			}
			 
		} 
		catch (FileNotFoundException e) {
			System.out.println("Unable to open file: " + filename);
		}
		catch(IOException e) {
			System.out.println("Error reading file: " + filename);
		}	
		finally {
			if (dis != null) {
				try {
					dis.close();
				} catch (IOException e) {
					System.out.println("Error closing file: " + filename);
				}
			}
		}	
	}

	/**
	 * Get the width of the image, or -1 if the file has not yet been read.
	 * 
	 * @return the width of the image or -1.
	 */
	public int getWidth() {
		if (width == 0) {
			return -1; 
		}
		else {
			return width;
		}	
	}

	/**
	 * Get the height of the image or -1 if the file has not yet been read.
	 * 
	 * @return the height of the image or -1.
	 */
	public int getHeight() {
		if (height == 0) {
			return -1;
		}
		else {
			return height;
		}
	}

	/**
	 * Get the intensity (average of the RGB values) of the pixel at row, col in
	 * the .bmp file.
	 * 
	 * @param row
	 *            the row of the pixel
	 * @param col
	 *            the column of the pixel
	 * @return the intensity of the pixel at row, col, or -1 if the file has not
	 *         been read.
	 */
	public int getIntensity(int row, int col) {
		if (width == 0 || height == 0) {
			return -1;
		}
		else {
			return pixelList[row][col];
		}
	}

	/**
	 * Write the ASCII art rendering of the .bmp file into a text file using the
	 * name specified in the constructor (adding a .txt extension).
	 */
	public void writeFile() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(filename+".txt", false));
		
			for(int i = height-1; i >= 0; i--) { 
				for(int j = 0; j < width; j++) {
					if(pixelList[i][j] >= 0 && pixelList[i][j] < 32) {
						pw.print("@");
					} 
					else if(pixelList[i][j] >= 32 && pixelList[i][j] < 64) {
						pw.print("0");
					}
					else if(pixelList[i][j] >= 64 && pixelList[i][j] < 96) {
						pw.print("O");
					}
					else if(pixelList[i][j] >= 96 && pixelList[i][j] < 128) {
						pw.print("o");
					}
					else if(pixelList[i][j] >= 128 && pixelList[i][j] < 160) {
						pw.print(":");
					}
					else if(pixelList[i][j] >= 160 && pixelList[i][j] < 192) {
						pw.print(",");
					}
					else if(pixelList[i][j] >= 192 && pixelList[i][j] < 224) {
						pw.print(".");
					}
					else if(pixelList[i][j] >= 224 && pixelList[i][j] < 256) {
						pw.print(" ");
					}
				}
				pw.print("\n");
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Unable to open file: " + filename);
		}
		finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	/**
	 * 
	 *Reads the BMP file and outputs an ASCII art rendering to the same location
	 *with a .txt extension
	 * @param args  none
	 */ 
	public static void main(String[] args) {
		
		BMPtoASCII bta = new BMPtoASCII("src/lab06/asciiart/images/monalisa");
		bta.readFile();
		bta.writeFile();
	}
}
