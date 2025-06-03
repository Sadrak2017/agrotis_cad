package org.agrotis.cad.services;

import org.agrotis.cad.messages.ApiResponse;
import org.agrotis.cad.repositories.LaboratoryRepository;
import org.agrotis.cad.repositories.PropertyRepository;
import org.agrotis.cad.repositories.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
  @Autowired
  public LaboratoryRepository laboratoryRepository;
  @Autowired
  public PropertyRepository propertyRepository;
  @Autowired
  public RegisterRepository registerRepository;


  public ResponseEntity<ApiResponse> get_properties() {
    return null;
  }

  public ResponseEntity<ApiResponse> get_laboratories() {
    return null;
  }
}
