package com.ktd.service.afw.core.model;

public enum InstanceStatus {
  START((short) 1),
  STOP((short) 2);


  public final short id;

  /**
   * 私有构造函数
   *
   * @param id
   */
  InstanceStatus(short id) {
    this.id = id;
  }
}
