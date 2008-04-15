/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: M:\\projects\\Castor\\src\\com\\eldergods\\Castor\\Service\\ICastorStation.aidl
 */
package com.eldergods.Castor.Service;
import java.lang.String;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Binder;
import android.os.Parcel;
public interface ICastorStation extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.eldergods.Castor.Service.ICastorStation
{
private static final java.lang.String DESCRIPTOR = "com.eldergods.Castor.Service.ICastorStation";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an ICastorStation interface,
 * generating a proxy if needed.
 */
public static com.eldergods.Castor.Service.ICastorStation asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
com.eldergods.Castor.Service.ICastorStation in = (com.eldergods.Castor.Service.ICastorStation)obj.queryLocalInterface(DESCRIPTOR);
if ((in!=null)) {
return in;
}
return new com.eldergods.Castor.Service.ICastorStation.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags)
{
try {
switch (code)
{
case TRANSACTION_getName:
{
java.lang.String _result = this.getName();
reply.writeString(_result);
return true;
}
case TRANSACTION_getTags:
{
java.lang.String[] _result = this.getTags();
reply.writeStringArray(_result);
return true;
}
case TRANSACTION_getUrl:
{
java.lang.String _result = this.getUrl();
reply.writeString(_result);
return true;
}
case TRANSACTION_record:
{
this.record();
return true;
}
case TRANSACTION_play:
{
this.play();
return true;
}
case TRANSACTION_stop:
{
this.stop();
return true;
}
case TRANSACTION_addScheduledTime:
{
this.addScheduledTime();
return true;
}
case TRANSACTION_getStatus:
{
java.lang.String _result = this.getStatus();
reply.writeString(_result);
return true;
}
}
}
catch (android.os.DeadObjectException e) {
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.eldergods.Castor.Service.ICastorStation
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getName() throws android.os.DeadObjectException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
mRemote.transact(Stub.TRANSACTION_getName, _data, _reply, 0);
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public java.lang.String[] getTags() throws android.os.DeadObjectException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String[] _result;
try {
mRemote.transact(Stub.TRANSACTION_getTags, _data, _reply, 0);
_result = _reply.createStringArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public java.lang.String getUrl() throws android.os.DeadObjectException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
mRemote.transact(Stub.TRANSACTION_getUrl, _data, _reply, 0);
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void record() throws android.os.DeadObjectException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
mRemote.transact(Stub.TRANSACTION_record, _data, null, 0);
}
finally {
_data.recycle();
}
}
public void play() throws android.os.DeadObjectException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
mRemote.transact(Stub.TRANSACTION_play, _data, null, 0);
}
finally {
_data.recycle();
}
}
public void stop() throws android.os.DeadObjectException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
mRemote.transact(Stub.TRANSACTION_stop, _data, null, 0);
}
finally {
_data.recycle();
}
}
public void addScheduledTime() throws android.os.DeadObjectException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
mRemote.transact(Stub.TRANSACTION_addScheduledTime, _data, null, 0);
}
finally {
_data.recycle();
}
}
public java.lang.String getStatus() throws android.os.DeadObjectException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
mRemote.transact(Stub.TRANSACTION_getStatus, _data, _reply, 0);
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getName = (IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getTags = (IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getUrl = (IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_record = (IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_play = (IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_stop = (IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_addScheduledTime = (IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getStatus = (IBinder.FIRST_CALL_TRANSACTION + 7);
}
public java.lang.String getName() throws android.os.DeadObjectException;
public java.lang.String[] getTags() throws android.os.DeadObjectException;
public java.lang.String getUrl() throws android.os.DeadObjectException;
public void record() throws android.os.DeadObjectException;
public void play() throws android.os.DeadObjectException;
public void stop() throws android.os.DeadObjectException;
public void addScheduledTime() throws android.os.DeadObjectException;
public java.lang.String getStatus() throws android.os.DeadObjectException;
}
