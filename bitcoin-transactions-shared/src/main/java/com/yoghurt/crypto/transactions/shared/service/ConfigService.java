package com.yoghurt.crypto.transactions.shared.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.yoghurt.crypto.transactions.shared.domain.config.RetrievalHookConfig;
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException;

@RemoteServiceRelativePath("config")
public interface ConfigService extends RemoteService {

  boolean isPasswordPresent() throws ApplicationException;

  boolean isAuthentic(String passwordHashed) throws ApplicationException;

  /**
   * Update the password challenge phrase. Service will check if the old password is valid
   * or throw an error. Will always set the password if there is no existing password.
   * 
   * @param oldPasswordHashed The hashed current password, or null if there is none.
   * @param newPasswordHashed The hashed new password
   * @throws ApplicationException
   */
  void updateServicePassword(String currentPasswordHashed, String newPasswordHashed) throws ApplicationException;

  /**
   * Configure the service to use the given retrieval hook config.
   * 
   * @param passwordHashed Valid hashed password.
   * @param config RetrievalHookConfig
   * @throws ApplicationException
   */
  void setBlockchainHookConfig(String passwordHashed, RetrievalHookConfig config) throws ApplicationException;

  RetrievalHookConfig getCurrentConfig(String passwordHashed) throws ApplicationException;

  /**
   * Ask the service to attempt to auto-configure the given RetrievalHookConfig.
   * 
   * Depending on what's in RetrievalHookConfig this may succeed or fail. Void return means success.
   * 
   * @param passwordHashed Valid hashed password.
   * @param config RetrievalHookConfig (which may be empty but must be concretely typed)
   * @throws ApplicationException
   */
  RetrievalHookConfig attemptAutoConfig(String passwordHashed, RetrievalHookConfig config) throws ApplicationException;

}
