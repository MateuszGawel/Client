package com.mateusz.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class TimeService {
	private static final Logger LOGGER = Logger.getLogger(TimeService.class.getName());

	public static Long getServerTime() {
		try {
			String TIME_SERVER = "0.europe.pool.ntp.org";
			NTPUDPClient timeClient = new NTPUDPClient();
			InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);

			TimeInfo timeInfo = timeClient.getTime(inetAddress);
			long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();

			LOGGER.log(Level.INFO, "Current time: " + returnTime);
			return returnTime;

		} catch (UnknownHostException e) {
			LOGGER.log(Level.SEVERE, "Couldn't get server time", e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Couldn't get server time", e);
		}
		return null;
	}
}
