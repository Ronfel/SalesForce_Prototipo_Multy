// 
// Created by the DataSnap proxy generator.
// 15/09/2017 17:23:07
// 

package com.embarcadero.javaandroid;

import java.util.Date;

public class DSProxy {
  public static class TServerMethods1 extends DSAdmin {
    public TServerMethods1(DSRESTConnection Connection) {
      super(Connection);
    }
    
    
    private DSRESTParameterMetaData[] TServerMethods1_GetDados_Clientes_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethods1_GetDados_Clientes_Metadata() {
      if (TServerMethods1_GetDados_Clientes_Metadata == null) {
        TServerMethods1_GetDados_Clientes_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("sCodCli", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethods1_GetDados_Clientes_Metadata;
    }

    /**
     * @param sCodCli [in] - Type on server: string
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetDados_Clientes(String sCodCli) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethods1.GetDados_Clientes");
      cmd.prepare(get_TServerMethods1_GetDados_Clientes_Metadata());
      cmd.getParameter(0).getValue().SetAsString(sCodCli);
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(1).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethods1_PutDados_Clientes_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethods1_PutDados_Clientes_Metadata() {
      if (TServerMethods1_PutDados_Clientes_Metadata == null) {
        TServerMethods1_PutDados_Clientes_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("joClientes", DSRESTParamDirection.Input, DBXDataTypes.JsonValueType, "TJSONObject"),
          new DSRESTParameterMetaData("sCodVend", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.BooleanType, "Boolean"),
        };
      }
      return TServerMethods1_PutDados_Clientes_Metadata;
    }

    /**
     * @param joClientes [in] - Type on server: TJSONObject
     * @param sCodVend [in] - Type on server: string
     * @return result - Type on server: Boolean
     */
    public boolean PutDados_Clientes(TJSONObject joClientes, String sCodVend) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.POST);
      cmd.setText("TServerMethods1.PutDados_Clientes");
      cmd.prepare(get_TServerMethods1_PutDados_Clientes_Metadata());
      cmd.getParameter(0).getValue().SetAsJSONValue(joClientes);
      cmd.getParameter(1).getValue().SetAsString(sCodVend);
      getConnection().execute(cmd);
      return  cmd.getParameter(2).getValue().GetAsBoolean();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethods1_GetDados_Vendedor_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethods1_GetDados_Vendedor_Metadata() {
      if (TServerMethods1_GetDados_Vendedor_Metadata == null) {
        TServerMethods1_GetDados_Vendedor_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("sCodUserId", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethods1_GetDados_Vendedor_Metadata;
    }

    /**
     * @param sCodUserId [in] - Type on server: string
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetDados_Vendedor(String sCodUserId) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethods1.GetDados_Vendedor");
      cmd.prepare(get_TServerMethods1_GetDados_Vendedor_Metadata());
      cmd.getParameter(0).getValue().SetAsString(sCodUserId);
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(1).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethods1_GetDados_TabPreco_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethods1_GetDados_TabPreco_Metadata() {
      if (TServerMethods1_GetDados_TabPreco_Metadata == null) {
        TServerMethods1_GetDados_TabPreco_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethods1_GetDados_TabPreco_Metadata;
    }

    /**
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetDados_TabPreco() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethods1.GetDados_TabPreco");
      cmd.prepare(get_TServerMethods1_GetDados_TabPreco_Metadata());
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(0).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethods1_GetDados_Precos_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethods1_GetDados_Precos_Metadata() {
      if (TServerMethods1_GetDados_Precos_Metadata == null) {
        TServerMethods1_GetDados_Precos_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethods1_GetDados_Precos_Metadata;
    }

    /**
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetDados_Precos() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethods1.GetDados_Precos");
      cmd.prepare(get_TServerMethods1_GetDados_Precos_Metadata());
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(0).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethods1_GetDados_CondPag_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethods1_GetDados_CondPag_Metadata() {
      if (TServerMethods1_GetDados_CondPag_Metadata == null) {
        TServerMethods1_GetDados_CondPag_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethods1_GetDados_CondPag_Metadata;
    }

    /**
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetDados_CondPag() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethods1.GetDados_CondPag");
      cmd.prepare(get_TServerMethods1_GetDados_CondPag_Metadata());
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(0).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethods1_GetDados_Produtos_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethods1_GetDados_Produtos_Metadata() {
      if (TServerMethods1_GetDados_Produtos_Metadata == null) {
        TServerMethods1_GetDados_Produtos_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethods1_GetDados_Produtos_Metadata;
    }

    /**
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetDados_Produtos() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethods1.GetDados_Produtos");
      cmd.prepare(get_TServerMethods1_GetDados_Produtos_Metadata());
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(0).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethods1_GetDados_Pedidos_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethods1_GetDados_Pedidos_Metadata() {
      if (TServerMethods1_GetDados_Pedidos_Metadata == null) {
        TServerMethods1_GetDados_Pedidos_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("sCodVend", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethods1_GetDados_Pedidos_Metadata;
    }

    /**
     * @param sCodVend [in] - Type on server: string
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetDados_Pedidos(String sCodVend) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethods1.GetDados_Pedidos");
      cmd.prepare(get_TServerMethods1_GetDados_Pedidos_Metadata());
      cmd.getParameter(0).getValue().SetAsString(sCodVend);
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(1).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethods1_PutDados_Pedidos_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethods1_PutDados_Pedidos_Metadata() {
      if (TServerMethods1_PutDados_Pedidos_Metadata == null) {
        TServerMethods1_PutDados_Pedidos_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("joPedidos", DSRESTParamDirection.Input, DBXDataTypes.JsonValueType, "TJSONObject"),
          new DSRESTParameterMetaData("joItemPed", DSRESTParamDirection.Input, DBXDataTypes.JsonValueType, "TJSONObject"),
          new DSRESTParameterMetaData("sCodVend", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("sUserId", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.BooleanType, "Boolean"),
        };
      }
      return TServerMethods1_PutDados_Pedidos_Metadata;
    }

    /**
     * @param joPedidos [in] - Type on server: TJSONObject
     * @param joItemPed [in] - Type on server: TJSONObject
     * @param sCodVend [in] - Type on server: string
     * @param sUserId [in] - Type on server: string
     * @return result - Type on server: Boolean
     */
    public boolean PutDados_Pedidos(TJSONObject joPedidos, TJSONObject joItemPed, String sCodVend, String sUserId) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.POST);
      cmd.setText("TServerMethods1.PutDados_Pedidos");
      cmd.prepare(get_TServerMethods1_PutDados_Pedidos_Metadata());
      cmd.getParameter(0).getValue().SetAsJSONValue(joPedidos);
      cmd.getParameter(1).getValue().SetAsJSONValue(joItemPed);
      cmd.getParameter(2).getValue().SetAsString(sCodVend);
      cmd.getParameter(3).getValue().SetAsString(sUserId);
      getConnection().execute(cmd);
      return  cmd.getParameter(4).getValue().GetAsBoolean();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethods1_GetDados_Agendamentos_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethods1_GetDados_Agendamentos_Metadata() {
      if (TServerMethods1_GetDados_Agendamentos_Metadata == null) {
        TServerMethods1_GetDados_Agendamentos_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TServerMethods1_GetDados_Agendamentos_Metadata;
    }

    /**
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetDados_Agendamentos() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TServerMethods1.GetDados_Agendamentos");
      cmd.prepare(get_TServerMethods1_GetDados_Agendamentos_Metadata());
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(0).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TServerMethods1_PutDados_Agendamentos_Metadata;
    private DSRESTParameterMetaData[] get_TServerMethods1_PutDados_Agendamentos_Metadata() {
      if (TServerMethods1_PutDados_Agendamentos_Metadata == null) {
        TServerMethods1_PutDados_Agendamentos_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("joAgendamentos", DSRESTParamDirection.Input, DBXDataTypes.JsonValueType, "TJSONObject"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.BooleanType, "Boolean"),
        };
      }
      return TServerMethods1_PutDados_Agendamentos_Metadata;
    }

    /**
     * @param joAgendamentos [in] - Type on server: TJSONObject
     * @return result - Type on server: Boolean
     */
    public boolean PutDados_Agendamentos(TJSONObject joAgendamentos) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.POST);
      cmd.setText("TServerMethods1.PutDados_Agendamentos");
      cmd.prepare(get_TServerMethods1_PutDados_Agendamentos_Metadata());
      cmd.getParameter(0).getValue().SetAsJSONValue(joAgendamentos);
      getConnection().execute(cmd);
      return  cmd.getParameter(1).getValue().GetAsBoolean();
    }
  }

}
