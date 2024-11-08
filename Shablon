
﻿using System;
using System.ComponentModel;
using System.ComponentModel.Design;

public interface IReport
{
    string Generate();
}
public class SalesReport : IReport
{
    public string Generate() => "sales Report data";
}
public class UserReport : IReport
{
    public string Generate() => "User Report data";
}
public abstract class ReportDecorator : IReport
{
    protected IReport _report;

    protected ReportDecorator(IReport report)
    {
        _report = report;
    }
    public virtual string Generate()
    {
        return _report.Generate();
    }
}
public class DateFilterDecorator : ReportDecorator
{
    private readonly DateTime _startDate;
    private readonly DateTime _endDate;

    public DateFilterDecorator(IReport report, DateTime startDate, DateTime endDate) : base(report)
    {
        _startDate = startDate;
        _endDate = endDate;
    }

    public override string Generate()
    {
        return $@"{base.Generate()} filtered by date from
{_startDate.ToShortDateString()} to {_endDate.ToShortDateString()}";
    }

}
public class SortingDecorator : ReportDecorator
{ 
    private readonly string _sortBy;

    public SortingDecorator(IReport report, string sortBy) : base(report)
    {
        _sortBy = sortBy;
    }
    public override string Generate()
    {
        return base.Generate();
    }
}

public class CsvExportDecorator : ReportDecorator
{
    public CsvExportDecorator(IReport report) : base(report) { }

    public override string Generate()
    {
        return base.Generate();
    }
}
public static class ReportClient
{
    public static void GenerateReport()
    { 
    IReport report = new SalesReport();
        report = new DateFilterDecorator(report, DateTime.Now.AddMonths(-1), DateTime.Now);
        report = new SortingDecorator(report, "date");
        report = new CsvExportDecorator(report);

        Console.WriteLine(report.Generate());
    }
}
public interface IIternalDeliveryService
{
    void DeliverOrder(string orderId);
    string GetDeliveryStatus(string orderId);
}
public class InternalDeliveryService : IInheritanceService
{
    public void AddInheritedComponents(IComponent component, IContainer container)
    {
        throw new NotImplementedException();
    }

    public void DeliverOrder(string orderId)
    {
        Console.WriteLine($"Delivering order {orderId} via internal service.");

    }
    public string GetDeliveryStatus(String orderId)
    {
        return $"Order {orderId} status: Delivered (internal)";

    }

    public InheritanceAttribute GetInheritanceAttribute(IComponent component)
    {
        throw new NotImplementedException();
    }
}
public class ExternalLogisticsServiceA
{
    public void ShipItem(String itemId) => Console.WriteLine($"Shipping item{itemId} via service A");
    public string Trackshipment(int shipmentID) => $"Shipment {shipmentID} status: In transit (Serveice A)";

    internal void ShipItem(object itemId)
    {
        throw new NotImplementedException();
    }
}
public class ExternalLogisticsServiceB
{
    public void sendPackage(string packageInfo) => Console.WriteLine($"Sending package {packageInfo} via Service B");
    public string CheckPackagestatus(string trackingCode) => $"Package{trackingCode} status: Delivered (Service B)";

}
public class LogisticsAdapterA : IInheritanceService
{
    private readonly ExternalLogisticsServiceA _externalServiceA = new ExternalLogisticsServiceA();
    private string itemId;

    public void AddInheritedComponents(IComponent component, IContainer container)
    {
        throw new NotImplementedException();
    }

    public void DeliverOrder(string orderId)
    {
        if (int.TryParse(orderId, out int ItemId))
            _externalServiceA.ShipItem(itemId);
        else
            Console.WriteLine("Invalid order ID format.");
    }
    public string GetDeliveryStatus(string orderId)
    {
        if(int.TryParse(orderId,out int shipmentId))
            return _externalServiceA.Trackshipment(shipmentId);

        return "Invalid order ID Format.";
    }

    public InheritanceAttribute GetInheritanceAttribute(IComponent component)
    {
        throw new NotImplementedException();
    }
}
public class LogisticsAdapterB : IIternalDeliveryService
{
    private readonly ExternalLogisticsServiceB _externalServiceB = new ExternalLogisticsServiceB();

    public void DeliverOrder(string orderId)
    {
        throw new NotImplementedException();
    }

    public void DeliveryOrder(string orderId)
    {
        _externalServiceB.sendPackage(orderId);
    }
    public string GetDeliveryStatus(string orderId)
    {
        return _externalServiceB.CheckPackagestatus(orderId);
    }
}

public static class DeliveryServicFactory
{
    public static IInternalDeliveryService GetDeliveryService(string type)
    {
        switch(type)
        {
            case "Internal":
                return new InternalDeliveryService();
            case "ServiceA":
                return new LogisticsAdapterA();
            case "ServiceB":
                return new LogisticsAdapterB();
            default:
                throw new ArgumentException("Invalid service type");

        }
    }
}
public static class LogisticsClient
{
    public static void ProcessDelivery()
    {
        IInternalDeliveryService deliveryService = new DeliveryServicFactory.GetDeliveryService("ServiceA");
        deliveryService.DeliverOrder("123");
        Console.WriteLine(deliveryService.DeliveryStatus("123"));
    }
}
// Vhod
public class program
{
    public static void Main()
    {
        Console.WriteLine("----- Сиситема отчетности ----");
        ReportClient.GenerateReport();

        Console.WriteLine("------Система логистики -----");
        LogisticsClient.ProcessDelivery();
    }
}
