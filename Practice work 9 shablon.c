1 задание 
using System;
using System.Collections.Generic;
using System.Linq;

// Интерфейс IReport для генерации отчетов
public interface IReport
{
    string Generate();
}

// Класс отчета по продажам
public class SalesReport : IReport
{
    public string Generate()
    {
        return "Sales Report Data";
    }
}

// Класс отчета по пользователям
public class UserReport : IReport
{
    public string Generate()
    {
        return "User Report Data";
    }
}

// Абстрактный декоратор для отчетов
public abstract class ReportDecorator : IReport
{
    protected IReport _report;

    public ReportDecorator(IReport report)
    {
        _report = report;
    }

    public virtual string Generate()
    {
        return _report.Generate();
    }
}

// Декоратор для фильтрации по дате
public class DateFilterDecorator : ReportDecorator
{
    private DateTime _startDate;
    private DateTime _endDate;

    public DateFilterDecorator(IReport report, DateTime startDate, DateTime endDate) : base(report)
    {
        _startDate = startDate;
        _endDate = endDate;
    }

    public override string Generate()
    {
        return $"{_report.Generate()} | Filtered by dates from {_startDate.ToShortDateString()} to {_endDate.ToShortDateString()}";
    }
}

// Декоратор для сортировки данных
public class SortingDecorator : ReportDecorator
{
    private string _sortBy;

    public SortingDecorator(IReport report, string sortBy) : base(report)
    {
        _sortBy = sortBy;
    }

    public override string Generate()
    {
        return $"{_report.Generate()} | Sorted by {_sortBy}";
    }
}

// Декоратор для экспорта в CSV
public class CsvExportDecorator : ReportDecorator
{
    public CsvExportDecorator(IReport report) : base(report) { }

    public override string Generate()
    {
        return $"{_report.Generate()} | Exported to CSV format";
    }
}

// Декоратор для экспорта в PDF
public class PdfExportDecorator : ReportDecorator
{
    public PdfExportDecorator(IReport report) : base(report) { }

    public override string Generate()
    {
        return $"{_report.Generate()} | Exported to PDF format";
    }
}

// Клиентский код
class Program
{
    static void Main(string[] args)
    {
        IReport report = new SalesReport();
        report = new DateFilterDecorator(report, DateTime.Now.AddMonths(-1), DateTime.Now);
        report = new SortingDecorator(report, "Date");
        report = new CsvExportDecorator(report);

        Console.WriteLine(report.Generate());

        // Пример для UserReport с PDF экспортом
        IReport userReport = new UserReport();
        userReport = new SortingDecorator(userReport, "Name");
        userReport = new PdfExportDecorator(userReport);

        Console.WriteLine(userReport.Generate());
    }
}
2 задание 
using System;

// Интерфейс внутренней службы доставки
public interface IInternalDeliveryService
{
    void DeliverOrder(string orderId);
    string GetDeliveryStatus(string orderId);
}

// Реализация внутренней службы доставки
public class InternalDeliveryService : IInternalDeliveryService
{
    public void DeliverOrder(string orderId)
    {
        Console.WriteLine($"Delivering order {orderId} via Internal Delivery.");
    }

    public string GetDeliveryStatus(string orderId)
    {
        return $"Status of order {orderId} in Internal Delivery.";
    }
}

// Внешняя служба логистики A
public class ExternalLogisticsServiceA
{
    public void ShipItem(int itemId)
    {
        Console.WriteLine($"Shipping item {itemId} via External Logistics Service A.");
    }

    public string TrackShipment(int shipmentId)
    {
        return $"Tracking shipment {shipmentId} in External Logistics Service A.";
    }
}

// Адаптер для внешней службы логистики A
public class LogisticsAdapterA : IInternalDeliveryService
{
    private readonly ExternalLogisticsServiceA _service;

    public LogisticsAdapterA(ExternalLogisticsServiceA service)
    {
        _service = service;
    }

    public void DeliverOrder(string orderId)
    {
        int itemId = int.Parse(orderId);
        _service.ShipItem(itemId);
    }

    public string GetDeliveryStatus(string orderId)
    {
        int shipmentId = int.Parse(orderId);
        return _service.TrackShipment(shipmentId);
    }
}

// Внешняя служба логистики B
public class ExternalLogisticsServiceB
{
    public void SendPackage(string packageInfo)
    {
        Console.WriteLine($"Sending package {packageInfo} via External Logistics Service B.");
    }

    public string CheckPackageStatus(string trackingCode)
    {
        return $"Status of package {trackingCode} in External Logistics Service B.";
    }
}

// Адаптер для внешней службы логистики B
public class LogisticsAdapterB : IInternalDeliveryService
{
    private readonly ExternalLogisticsServiceB _service;

    public LogisticsAdapterB(ExternalLogisticsServiceB service)
    {
        _service = service;
    }

    public void DeliverOrder(string orderId)
    {
        _service.SendPackage(orderId);
    }

    public string GetDeliveryStatus(string orderId)
    {
        return _service.CheckPackageStatus(orderId);
    }
}

// Фабрика для выбора службы доставки
public static class DeliveryServiceFactory
{
    public static IInternalDeliveryService GetDeliveryService(string serviceType)
    {
        return serviceType switch
        {
            "Internal" => new InternalDeliveryService(),
            "ExternalA" => new LogisticsAdapterA(new ExternalLogisticsServiceA()),
            "ExternalB" => new LogisticsAdapterB(new ExternalLogisticsServiceB()),
            _ => throw new ArgumentException("Invalid service type")
        };
    }
}

// Клиентский код
class Client
{
    static void Main(string[] args)
    {
        IInternalDeliveryService deliveryService = DeliveryServiceFactory.GetDeliveryService("ExternalA");
        deliveryService.DeliverOrder("123");
        Console.WriteLine(deliveryService.GetDeliveryStatus("123"));
        
        deliveryService = DeliveryServiceFactory.GetDeliveryService("ExternalB");
        deliveryService.DeliverOrder("ABC123");
        Console.WriteLine(deliveryService.GetDeliveryStatus("ABC123"));
    }
}

