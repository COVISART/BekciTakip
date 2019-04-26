<?php

/**
 * Created by PhpStorm.
 * User: nobat
 * Date: 7/18/2018
 * Time: 14:10
 */


use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;
require 'src/Exception.php';
require 'src/PHPMailer.php';
require 'src/SMTP.php';
$mail = new PHPMailer(TRUE);

/**
 * AppService.php?data=
 *
 * Daire:1$Konum:38.681134,27.312042$ID:1$Tarih:19/04/2019$Saat:19:30:11#
 * Daire:2$Konum:38.681134,27.312042$ID:2$Tarih:19/04/2019$Saat:19:32:57#
 * Daire:3$Konum:38.681134,27.312042$ID:3$Tarih:19/04/2019$Saat:19:51:39#
**/

//if (!isset($_GET["mail"]) == NULL && !isset($_GET["data"]) == NULL)
if (!isset($_GET["data"]) == NULL)
{
    $DataSet = $_GET["data"];
    $mailTarget = $_GET["mail"];

    $SavedReading = explode("y",$DataSet);
   if (count($SavedReading) > 0):

        $header="<table><thead><tr><th>"."Bekçi Günlük Rapor: ". date("d/m/Y")."</th></tr></thead>";
        $td = "";
        foreach ($SavedReading as $row):
            $td = $td."<tr><td>".$row."</td></tr>";
        endforeach;
        $footer = "</tbody></table>";

        echo $header.$td.$footer;

        try
        {
            $mail->IsSMTP();
            $mail->SMTPAuth = true;
            $mail->Host = "smtp.yandex.com";
            $mail->Port = 465;
            $mail->SMTPSecure = 'ssl';
            $mail->SMTPOptions = array (
                'ssl' => array(
                    'verify_peer' => false,
                    'verify_peer_name' => false,
                    'allow_self_signed' => true)
            );

            $mail->Username = "bekci@covisart.com";
            $mail->Password = "*Nobat21*";

            /* Set the mail sender. */
            $mail->setFrom('bekci@covisart.com', 'bekci@covisart.com');

            $mail->CharSet = 'UTF-8';
            /* Add a recipient. */
            $mail->addAddress("nobatgeldi@outlook.com", "Nobatgeldi Geldimammedov");

            /* Set the subject. */
            $mail->Subject = 'Bekçi Günlük Rapor:'.date("d/m/Y");

            /*Create an HTML email message*/
            $mail->isHTML(TRUE);

            /* Set the mail message body. */
            $mail->Body = $header.$td.$footer;

            /* Finally send the mail. */
            //send the message, check for errors
            if (!$mail->send()) {
                echo 'Mailer Error: ' . $mail->ErrorInfo;
            } else {
                echo 'Message sent!';
            }
        }
        catch (Exception $e)
        {
           /* PHPMailer exception. */
           echo $e->errorMessage();
        }
        catch (\Exception $e)
        {
           /* PHP exception (note the backslash to select the global namespace Exception class). */
           echo $e->getMessage();
        }
   endif;
}

