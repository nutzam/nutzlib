package org.nutzx.robot.site;

import java.io.File;

import org.nutz.lang.Files;

public class Robot {

	public static void main(String[] args) throws Exception {
		File src = Files.findFile(args[0]);
		File dest = Files.findFile(args[1]);
		SiteRobot robot = new SiteRobot();
		robot.transform(src, dest);
	}

}
