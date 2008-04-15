package com.eldergods.Castor.Service;

interface ICastorStation
{
	String getName();
	String[] getTags();
	String getUrl();

	void record();
	void play();
	void stop();

	void addScheduledTime();

	String getStatus();
}